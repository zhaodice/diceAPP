package org.mirai.zhao.dice.web
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

object HttpHelper{
    class OptionInfo{
        lateinit var referer:String
        lateinit var cookies:String
        lateinit var user_agent:String
    }

    /**
     * @功能 下载临时素材接口
     * @param savePath 文件将要保存的目录
     * @param url 请求的路径
     * @return
     */
    fun saveUrlAs(url: String, savePath: String):Boolean {
        //System.out.println("fileName---->"+filePath);
        var filePath = savePath
        val fileOut: FileOutputStream
        val conn: HttpURLConnection
        val inputStream: InputStream
        //创建不同的文件夹目录
        val file=File(filePath)
        try {
            file.parentFile.apply {
                if(this!=null) {
                    if (!exists()) {
                        mkdirs()
                    }
                }
            }
            file.apply {
                if(!exists())
                    createNewFile()
            }

            // 建立链接
            val httpUrl = URL(url)
            conn = httpUrl.openConnection() as HttpURLConnection
            //以Post方式提交表单，默认get方式
            conn.requestMethod = "GET"
            conn.doInput = true
            // post方式不能使用缓存
            conn.useCaches = false
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
            //连接指定的资源
            conn.connect()
            //获取网络输入流
            inputStream = conn.inputStream
            val bis = BufferedInputStream(inputStream)
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/"
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = FileOutputStream(filePath)
            val bos = BufferedOutputStream(fileOut)
            val buf = ByteArray(4096)
            var length = bis.read(buf)
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length)
                length = bis.read(buf)
            }
            bos.close()
            bis.close()
            conn.disconnect()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        file.delete()
        return false
    }
    fun getData(postURL: String, optionInfo: OptionInfo?):String {//访问准备
        try{
            val url = URL(postURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 3000
            conn.readTimeout = 5000
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("Connection", "Keep-Alive")
            if(optionInfo==null)
                conn.setRequestProperty("User-Agent", "okhttp/4.2.2")
            else{
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br")
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9")
                conn.setRequestProperty("Connection", "keep-alive")
                conn.setRequestProperty("Content-Length", "0")
                conn.setRequestProperty("Referer", optionInfo.referer)
                conn.setRequestProperty("Sec-Fetch-Dest", "empty")
                conn.setRequestProperty("Sec-Fetch-Mode", "cors")
                conn.setRequestProperty("Sec-Fetch-Site", "same-origin")
                conn.setRequestProperty("User-Agent", optionInfo.user_agent)
            }

            val `in`: Reader = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"))
            val sb = StringBuilder()
            var c: Int
            while (`in`.read().also { c = it } >= 0) {
                sb.append(c.toChar())
            }
            val response = sb.toString()
            //println(response)
            if(optionInfo!=null){
                var sessionId = ""
                var cookieVal:String
                var key: String?
                var i = 1
                while (conn.getHeaderFieldKey(i).also { key = it } != null) {
                    if (key.equals("set-cookie", ignoreCase = true)) {
                        cookieVal = conn.getHeaderField(i)
                        cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"))
                        sessionId = "$sessionId$cookieVal;"
                    }
                    i++
                }
                optionInfo.cookies=sessionId
            }
            return response
        }catch (e: Throwable){
            //e.printStackTrace()
            return ""
        }
    }
    fun postData(postURL: String, params: HashMap<String, Any>, optionInfo: OptionInfo?):String{
        val postData = StringBuilder()
        for ((key, value) in params) {
            if (postData.isNotEmpty()) postData.append('&')
            postData.append(URLEncoder.encode(key, "UTF-8"))
            postData.append('=')
            postData.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }
        val postDataBytes = postData.toString().toByteArray(charset("UTF-8"))
        return postData(postURL,postDataBytes,optionInfo)
    }
    fun postData(postURL: String, params: String, optionInfo: OptionInfo?):String {
        return postData(postURL,params.toByteArray(Charsets.UTF_8),optionInfo)
    }
    fun postData(postURL: String, postDataBytes: ByteArray, optionInfo: OptionInfo?):String{
        val url = URL(postURL)

        //println("POST DATA:$postData ->>>$postURL")
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 3000
        conn.readTimeout = 3000

        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length", postDataBytes.size.toString())
        conn.setRequestProperty("Connection", "Keep-Alive")

        if(optionInfo==null)
            conn.setRequestProperty("User-Agent", "okhttp/4.2.2")
        else{
            conn.setRequestProperty("Accept", "application/json")
            conn.setRequestProperty("Accept-Encoding", "deflate, br")
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9")
            conn.setRequestProperty("Connection", "keep-alive")
            conn.setRequestProperty("Cookie", optionInfo.cookies)
            conn.setRequestProperty("Referer", optionInfo.referer)
            conn.setRequestProperty("Sec-Fetch-Dest", "empty")
            conn.setRequestProperty("Sec-Fetch-Mode", "cors")
            conn.setRequestProperty("Sec-Fetch-Site", "same-origin")
            conn.setRequestProperty("User-Agent", optionInfo.user_agent)
        }
        conn.doOutput = true
        conn.outputStream.write(postDataBytes)
        val `in`: Reader = BufferedReader(InputStreamReader(conn.getInputStream(), "UTF-8"))
        val sb = StringBuilder()
        var c: Int
        while (`in`.read().also { c = it } >= 0) {
            sb.append(c.toChar())
        }
        //println(response)
        return sb.toString()
    }
}