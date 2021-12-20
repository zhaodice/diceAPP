package org.mirai.zhao.dice.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.ApplicationLoginSolver
import org.mirai.zhao.dice.IConsoleService
import org.mirai.zhao.dice.R
import org.mirai.zhao.dice.component.FixedTextView
import java.io.InputStream


class UnsafeLoginActivity : AppCompatActivity() {
    private lateinit var iConsoleService:IConsoleService
    private lateinit var unsafeLoginWeb : WebView
    private lateinit var refreshUnsafeWeb: SwipeRefreshLayout
    private lateinit var copyUrlBtn:Button
    private lateinit var notice: FixedTextView
    private lateinit var url:String
    private lateinit var currentUrl:String
    private var enforceFinishedStep = 0
    companion object{
        private val disguisedUserAgent = "Mozilla/5.0 (Linux; Android ${Build.VERSION.RELEASE}; ${Build.MODEL} Build/${Build.ID}; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045714 Mobile Safari/537.36 V1_AND_SQ_8.3.3_1376_YYB_D QQ/8.3.3.4515 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/70 SimpleUISwitch/1 QQTheme/2921"
        fun startActivity(url:String,context: Context){
            val notifyIntent = Intent(context, UnsafeLoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            notifyIntent.putExtra("url", url)
            context.startActivity(notifyIntent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unsafe_login)
        (AppContext.context as AppContext).connectConsoleService().ifDisconnect {
            finish()
        }.iConsoleService.apply {
            if(this==null){
                finish()
            }else{
                iConsoleService = this
            }
        }
        refreshUnsafeWeb=findViewById(R.id.refresh_unsafe_web)
        unsafeLoginWeb=findViewById(R.id.unsafe_login_web)
        copyUrlBtn=findViewById(R.id.forceStopUnsafeLogin)
        notice=findViewById(R.id.notice)

        if(enforceFinishedStep==0){
            copyUrlBtn.text = "无法完成身份验证？点我复制链接！"
            copyUrlBtn.setOnClickListener{
                val clip: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip = ClipData.newPlainText("text", url)
                clip.setPrimaryClip(myClip)

                val alterDialog = AlertDialog.Builder(this)
                alterDialog.setTitle("成功")
                alterDialog.setMessage("链接已经复制到了剪切板，现在你需要做\n" +
                        "1.【不要返回，按HOME切换】切换APP到你正准备认证的QQ\n" +
                        "2.然后粘贴链接到没人看见的地方，比如“我的电脑”\n" +
                        "3.点入链接，进入二维码认证\n" +
                        "4.长按二维码，点击“扫描二维码”，完成认证\n" +
                        "5.回到本APP，点击“我已在外面完成认证”")
                alterDialog.setPositiveButton("我已在外面完成认证") { dialogInterface, _ -> dialogInterface.cancel() }
                alterDialog.setOnCancelListener { authFinish("") }
                alterDialog.show()
            }
        }

        initWebView()
        intent.getStringExtra("url").apply {
            if(this==null){
                finish()
                return
            }else{
                url = this
            }
        }
        if(url.startsWith("https://ti.qq.com/safe/verify")){
            unsafeLoginWeb.loadUrl(url.replaceFirst("verify", "qrcode"))
        }else{
            unsafeLoginWeb.loadUrl(url)
        }
        currentUrl = unsafeLoginWeb.url as String
        notice.text = "当页面加载出现问题无法继续时，记得点右下角按钮获得帮助"
        refreshUnsafeWeb.setOnRefreshListener {
            unsafeLoginWeb.reload()
            lifecycleScope.launch {
                delay(1000)
                runOnUiThread {
                    refreshUnsafeWeb.isRefreshing = false
                }
            }
        }
        //  Toast.makeText(this, "请在完成验证后点击右上角继续登录", Toast.LENGTH_LONG).show()
    }
    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun initWebView() {
        //滑块验证码js注入，便于获取ticks
        unsafeLoginWeb.webViewClient = object : WebViewClient() {
            //https://framework.cdn-go.cn/AegisReport/v1.1.0/index.js
            val regexAegisReport = "https://.*/AegisReport/.*/index\\.js".toRegex()
            //https://security-web.cdn-go.cn/ti.qq.com_safe_verify/61b59645/app/qrcode/dist/cdn/index.bundle.js
            val regexQrcode = "https://.*/ti\\.qq\\.com_safe_verify/.*/app/qrcode/dist/cdn/index\\.bundle\\.js".toRegex()
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                if(Build.VERSION_CODES.O > Build.VERSION.SDK_INT) {
                    try {
                        // https://babeljs.io/repl 将其变成ES5格式,解决低版本安卓二维码无法显示的问题
                        val path = request.url.toString()
                        var localCopy: InputStream? = null
                        if (path.matches(regexAegisReport)) {
                            localCopy = assets.open("ti.qq.com/AegisReport.js")
                        } else if (path.matches(regexQrcode)) {
                            localCopy = assets.open("ti.qq.com/qrcode.js")
                        }
                        if (localCopy != null) {
                            return WebResourceResponse("application/x-javascript",
                                    "UTF-8",
                                    200,
                                    "OK",
                                    HashMap<String, String>().apply { put("Access-Control-Allow-Origin", "ti.qq.com") },
                                    localCopy)
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                if (url != null && view != null) {
                    if (url.startsWith("https://ssl.captcha.qq.com/template/wireless_mqq_captcha.html")) {//滑块验证码
                        notice.text = getString(R.string.slidNotice)
                    }else if (Build.VERSION_CODES.O > Build.VERSION.SDK_INT && url.startsWith("https://ti.qq.com/safe/qrcode")) {
                        notice.text = getString(R.string.qrcodeNotice)
                    }
                    val js = "mqq.invoke = function(a,b,c){return bridge.invoke(a,b,JSON.stringify(c))};"
                    view.evaluateJavascript(js) {}
                }
                super.onPageFinished(view, url)
            }
        }
        unsafeLoginWeb.addJavascriptInterface(Bridge(), "bridge")
        unsafeLoginWeb.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                // 按下回到qq按钮之后会打印这句话，于是就用这个解决了。。。。
                if (consoleMessage?.message()?.startsWith("手Q扫码验证") == true) {
                    authFinish("")
                }
                return super.onConsoleMessage(consoleMessage)
            }
        }
        WebView.setWebContentsDebuggingEnabled(true)
        unsafeLoginWeb.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            blockNetworkImage = false
            userAgentString = disguisedUserAgent
        }
    }
    private fun authFinish(s: String) {
        try {
            ApplicationLoginSolver.setResult(s)
            iConsoleService.commitVerification(s)
        }catch (e: Throwable){
            e.printStackTrace()
        }
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (unsafeLoginWeb.canGoBack()) {
                unsafeLoginWeb.goBack()
                return true
            }
        }
        return false
    }
    inner class Bridge {
        @JavascriptInterface
        fun invoke(cls: String?, method: String?, data: String?) {
            if (data != null) {
                val jsData = JSONObject(data)
                if (method == "onVerifyCAPTCHA") {
                    authFinish(jsData.getString("ticket"))
                }
            }
        }
    }
}