package org.mirai.zhao.dice.data

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class LogBuilder {
    var logCallback: (String) -> Unit = fun(_){}
    private var limitLineNum=-1
    companion object{
        const val LEVEL_INFO=3
        const val LEVEL_DEBUG=2
        const val LEVEL_WARNING=1
        const val LEVEL_ERROR=0
    }

    class Log(
        private val level: Int,
        private val text:String,
    ){
        private val time=System.currentTimeMillis()
        override fun toString():String{
            val res: String
            val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
            val date = Date(time)
            res = simpleDateFormat.format(date)
            var levelStr="[UNKNOWN]"
            when(level){
                LEVEL_DEBUG ->
                    levelStr="<font color=\"#006699\">[DEBUG]</font>"
                LEVEL_INFO ->
                    levelStr="<font color=\"#009900\">[INFO]</font>"
                LEVEL_ERROR ->
                    levelStr="<font color=\"#990000\">[ERROR]</font>"
                LEVEL_WARNING ->
                    levelStr="<font color=\"#999933\">[WARNING]</font>"
            }
            return String.format("<font color=\"#333366\">[%s]</font> %s %s ",res,levelStr,text.replace("\n","<br/>"))
        }
    }
    private val list= ArrayList<Log>()
    fun append(level: Int, text: String?): LogBuilder {
        if(text!=null) {
            synchronized(this) {
                val log = Log(level, text)
                list.add(log)
                if (list.size > limitLineNum) {
                    list.removeAt(0)
                }
                logCallback(log.toString())
            }
        }
        return this
    }
    fun build():String{
        synchronized(this) {
            val sb = StringBuilder()
            for (log in list) {
                sb.append(log.toString()).append("<br/>")
            }
            return sb.toString()
        }
    }
    fun clear(): LogBuilder {
        synchronized(this) {
            list.clear()
            return this
        }
    }
    fun limit(lineNum:Int): LogBuilder {
        limitLineNum=lineNum
        return this
    }
}