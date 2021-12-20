package org.mirai.zhao.dice.console.util

import net.mamoe.mirai.spi.AudioToSilkService
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.md5
import net.mamoe.mirai.utils.toUHexString
import java.io.File
import java.util.*
import android.media.MediaFormat
import android.media.MediaExtractor
import kotlinx.coroutines.runInterruptible
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.audio.AudioType
import org.mirai.zhao.dice.audio.AudioUtil
import org.mirai.zhao.dice.excpetion.ConvertingException
import org.mirai.zhao.dice.file.TempFile
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.zip.CRC32
import kotlin.collections.ArrayList


object SilkConverterImpl: AudioToSilkService {
    private const val TARGET_SAMPLE_RATE = 24000
    private const val MAX_PCM_FILE = 16000000
    private val convertingIds = ArrayList<Long>()
    private val newTemplateFile get() = TempFile(tempDir.resolve(UUID.randomUUID().toString()))
    private val workDir = AppContext.context?.cacheDir?.resolve("covertWorkingDir").apply {
        if(this==null){
            throw IllegalStateException("Context not found!")
        }
        mkdirs()
    } as File
    private val cacheDir = workDir.resolve("cache").apply {
        mkdirs()
    }
    private val tempDir = workDir.resolve("temp").apply {
        apply {
            deleteRecursively()
            mkdirs()
        }
    }
    override suspend fun convert(source: ExternalResource): ExternalResource {
        val config = AccountsManager.shareData
        if(config!=null) {
            if(!config.getBoolean("silkEncoder",true)){
                return source
            }
        }
        try{
            val ret:ExternalResource = externalResourceConvertToSilk(source)
            if (ret != source) {
                source.closeInterruptible()
            }
            return ret
        }catch (e:Throwable){
            source.closeInterruptible()
            throw e
        }
    }

    /**
     * 获取流的CRC32码
     *
     * @return String
     */
    private fun getFileStreamCRC32(fileInputStream: FileInputStream): Long {
        val crc32 = CRC32()
        return try {
            val buffer = ByteArray(8192)
            var length: Int
            while (fileInputStream.read(buffer).also { length = it } != -1) {
                crc32.update(buffer, 0, length)
            }
            crc32.value
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            0
        } catch (e: IOException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 获取音频文件的采样率
     * @param fileFullName 文件完整路径
     * @return 采样率
     */
    private fun getMediaSampleRate(fileFullName: String): Int {
        val mExtractor = MediaExtractor()
        try {
            mExtractor.setDataSource(fileFullName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val format = mExtractor.getTrackFormat(0)
        //获取当前音频的采样率
        mExtractor.selectTrack(0)
        return format.getInteger(MediaFormat.KEY_SAMPLE_RATE)
    }
    /**
     * 创建新的缓存文件
     *
     * @param id 文件标识码
     * @return
     */
    private fun newCacheFile(id:String):File{return cacheDir.resolve(id)}
    /**
     * PCM文件转SILK文件
     *
     * @param pcmFile PCM文件
     * @param destination SILK文件
     * @param sampleRate PCM的采样率
     */
    private fun pcmToSilk(pcmFile:File, destination:File, sampleRate:Int){
        if(sampleRate!= TARGET_SAMPLE_RATE) {
            println("Trying resampling sample rate of PCM $sampleRate to $TARGET_SAMPLE_RATE...")
            val resampledPCMFile = newTemplateFile
            AudioUtil.pcmResample(
                pcmFile.toString(),
                resampledPCMFile.toString(),
                sampleRate,
                TARGET_SAMPLE_RATE
            )
            pcmFile.delete()
            resampledPCMFile.renameTo(pcmFile)
        }
        println("Trying converting PCM to SILK..")
        AudioUtil.pcmToSilk(pcmFile.toString(), destination.toString(), TARGET_SAMPLE_RATE)
        println("Successful converting PCM to SILK!")
    }
    /**
     * 任意文件转SILK文件
     *
     * @param oriFile 源文件对象
     * @param destination SILK文件
     * @param type 文件类型
     * @return 是否成功
     */
    private fun anyToSilk(oriFile:File, destination:File, type: AudioType):Boolean{
        var successful = false
        val pcmFile = newTemplateFile
        try {
            if(type == AudioType.MP3) {
                println("Trying converting MP3 to PCM..sources")
                AudioUtil.mp3ToPCM(oriFile.absolutePath, pcmFile.absolutePath)
            }else if(type == AudioType.WAV) {
                println("Trying converting WAV to PCM..sources")
                val readSize = (oriFile.length()-44).toInt()
                val b = ByteArray(readSize)
                oriFile.inputStream().also { i->
                    i.read(b,44,readSize)
                    i.close()
                }
                pcmFile.writeBytes(b)
            }
            if(pcmFile.length()> MAX_PCM_FILE){//限制语音转换大小
                AndroidMiraiLogger.warning("音频文件过大，已自动裁剪，建议自行裁剪为合理长度。")
                val byteArray = ByteArray(MAX_PCM_FILE)
                pcmFile.inputStream().also { i->
                    i.read(byteArray,0, MAX_PCM_FILE)
                    i.close()
                }
                pcmFile.delete()
                pcmFile.writeBytes(byteArray)
            }
            pcmToSilk(pcmFile,destination, getMediaSampleRate(oriFile.absolutePath))
            successful = true
        }catch (e:Throwable){
            e.printStackTrace()
            destination.delete()
        }
        pcmFile.delete()
        return successful

    }
    /**
     * External resource 对象转支持格式
     *
     * @param source ExternalResource对象
     * @return 转换后的ExternalResource对象
     */
    private suspend fun externalResourceConvertToSilk(source: ExternalResource): ExternalResource {
        val oriFile= newTemplateFile
        val data:ByteArray
        source.inputStream().also { i->
            data = i.readBytes()
            runInterruptible {
                i.close()
            }
        }
        val type = if(data[0]==0x52.toByte() && data[1]==0x49.toByte() && data[2]==0x46.toByte() && data[3]==0x46.toByte()){
            AudioType.WAV
        }else if(data[0]==0x23.toByte() && data[1]==0x21.toByte() && data[2]==0x41.toByte() && data[3]==0x4D.toByte()) {
            AudioType.AMR
        }else if(data[0]==0x02.toByte() && data[1]==0x23.toByte() && data[2]==0x21.toByte() && data[3]==0x53.toByte()) {
            AudioType.SILK
        }else{
            oriFile.writeBytes(data)
            if(AudioUtil.mp3Detect(oriFile.absolutePath).also{ oriFile.delete() }) {
                AudioType.MP3
            }else{
                AudioType.OTHER
            }
        }
        when(type){
            AudioType.AMR, AudioType.SILK ->//directly supported
                return source
            AudioType.WAV, AudioType.MP3 -> {//need converting
                val cacheFile = newCacheFile(data.md5(0,data.size).toUHexString(""))
                if (!cacheFile.exists()){
                    if(!oriFile.exists()){
                        oriFile.writeBytes(data)
                    }
                    val fileId:Long
                    oriFile.inputStream().also { i->
                        fileId = getFileStreamCRC32(i)
                        runInterruptible {
                            i.close()
                        }
                    }
                    synchronized(convertingIds) {
                        if (convertingIds.contains(fileId)) {
                            throw ConvertingException("this resource is now converting so that it can't load immediately,please wait one or more minutes.(语音正在转换，请稍后触发)")
                        }
                    }
                    synchronized(fileId) {
                        convertingIds.add(fileId)
                        val tmp = newTemplateFile
                        if(anyToSilk(oriFile, tmp, type)) {
                            tmp.renameTo(cacheFile)
                        }else{
                            AndroidMiraiLogger.error("audio converting failed. ")
                        }
                        convertingIds.remove(fileId)
                    }
                }
                if(cacheFile.exists()) {
                    return cacheFile.readBytes().toExternalResource("silk").toAutoCloseable()
                }else{
                    throw ConvertingException("some wrong with converting process,audio converting failed.please report more information to developer.(音频转换失败)")
                }
            }
            else->{
                throw ConvertingException("this resource is not supported by application,please report more information to developer.(不支持的格式)")
            }
        }
    }
    private suspend fun ExternalResource.closeInterruptible(){
        runInterruptible {
            close()
        }
    }
    fun init(){
        AudioToSilkService.setService(SilkConverterImpl)
    }
}