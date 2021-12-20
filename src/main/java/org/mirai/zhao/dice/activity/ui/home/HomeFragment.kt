package org.mirai.zhao.dice.activity.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import org.mirai.zhao.dice.R

class HomeFragment : Fragment() {
    companion object{
        private var firstRun = true
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val homeWebView:WebView = root.findViewById(R.id.homeWebView)
        if(firstRun){
            homeWebView.clearCache(true)//在启动后的首次进入，清缓存
            firstRun=false
        }
        if(homeWebView.url == null) {
            homeWebView.loadUrl("https://app.trpgbot.com")
            homeWebView.webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView, request: WebResourceRequest?, error: WebResourceError?) {
                    super.onReceivedError(view, request, error)
                    view.loadUrl("file:///android_asset/404.html")
                }
            }
            homeWebView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                blockNetworkImage = false
            }
        }
        return root
    }
}