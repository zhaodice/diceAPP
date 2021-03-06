package org.mirai.zhao.dice.file

import java.io.*
import java.nio.charset.Charset

object TextFileOperator {
    private val UTF_8: Charset = Charset.forName("UTF-8")
    @JvmStatic
    fun read(f: File):String{
        synchronized(TextFileOperator.javaClass) {
            val sb = StringBuilder()
            if (!f.exists()) {
                return ""
            }
            val fis = FileInputStream(f)
            val isr = InputStreamReader(fis, UTF_8)
            val br = BufferedReader(isr)
            var line: String?
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append("\n") // 补上换行符
            }
            br.close()
            isr.close()
            fis.close()
            return sb.toString().replaceFirst("\\ufeff", "").trim()
        }
    }
    @JvmStatic
    fun write(f: File, s: String){
        synchronized(TextFileOperator.javaClass){
            val writerStream = FileOutputStream(f)
            val outputStreamWriter=OutputStreamWriter(writerStream, "UTF-8")
            val writer = BufferedWriter(outputStreamWriter)
            writer.write(s)
            writer.flush()
            writer.close()
            outputStreamWriter.close()
            writerStream.close()
        }
    }
}