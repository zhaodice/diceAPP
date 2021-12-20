package org.mirai.zhao.dice

import android.os.Build
import android.text.Html
import android.text.Spanned

object AndroidHtml {
    @Suppress("DEPRECATION")
    fun fromHtml(text:String):Spanned{
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        }else {
            Html.fromHtml(text)
        }
    }
}