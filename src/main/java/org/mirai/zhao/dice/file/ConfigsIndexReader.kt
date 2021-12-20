package org.mirai.zhao.dice.file

import android.content.Context
import org.json.JSONObject
import org.mirai.zhao.dice.AppContext
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object ConfigsIndexReader {
    fun read(context: Context):JSONObject{
        return try{
            JSONObject(TextFileOperator.read(File(AppContext.zhaoDiceRootData + "/extensionalOptionV2.txt")))
        }catch (e:Throwable){
            val input=context.assets.open("defaultConfig.txt")
            val inputStreamReader = BufferedReader(InputStreamReader(input))
            val text=inputStreamReader.readLines().joinToString("")
            inputStreamReader.close()
            JSONObject(text)
        }
    }
}