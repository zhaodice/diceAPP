package org.mirai.zhao.dice.activity.ui.console

import android.content.Intent
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import org.mirai.zhao.dice.*
import org.mirai.zhao.dice.component.FixedTextView
import org.mirai.zhao.dice.ControlServiceConnection


class ConsoleFragment : Fragment() {
    companion object{
        const val TAG = "ConsoleFragment"
    }
    private var autoRoll = false
    private var running = true
    private var consoleConnect:ConsoleConnect?=null
    class ConsoleConnect(val context:ConsoleFragment):Runnable{
        private var thread:Thread?=null
        private var connected=false
        private var run = true
        private var controlServiceConnection: ControlServiceConnection?=null
        private lateinit var iService:IConsoleService
        override fun run() {
            val appContext = AppContext.context as AppContext
            try{
                while (run){
                    val connect = appContext.connectConsoleService()
                    if(connect.mBound){
                        if(!connected) {
                            controlServiceConnection = connect
                            iService = connect.iConsoleService as IConsoleService
                            context.apply {
                                Handler(Looper.getMainLooper()).post {
                                    try {
                                        iService.registLogCallback(TAG,object : ILogCallback.Stub() {
                                            override fun logChanged(text: String) {
                                                Handler(Looper.getMainLooper()).post {
                                                    tv.append(AndroidHtml.fromHtml(text))
                                                    tv.append(AndroidHtml.fromHtml("<br/>"))
                                                    if (autoRoll) {
                                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                                                    }
                                                }
                                            }
                                        })
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            scrollView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
                                                val contentView: View = scrollView.getChildAt(0)
                                                if (contentView.measuredHeight == scrollView.scrollY + scrollView.height) {
                                                    btn.visibility = View.VISIBLE
                                                } else {
                                                    btn.visibility = View.INVISIBLE
                                                }
                                            }
                                        }
                                        try {
                                            tv.text = AndroidHtml.fromHtml(iService.logs)
                                        } catch (e: Throwable) {

                                        }
                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                                        //tv.visibility = View.VISIBLE
                                        connected = true
                                        println("重新连接mirai控制台成功")
                                    } catch (e: Throwable) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                    }else{
                        println("正在重新连接mirai控制台")
                        Handler(Looper.getMainLooper()).post {
                            context.tv.text = "载入中，请稍后..."
                        }
                        connected = false
                    }
                    Thread.sleep(1000)
                }
            }catch (e:InterruptedException){

            }
            println("正在断开mirai控制台")
            if(this::iService.isInitialized){
                try {
                    iService.unRegistLogCallback(TAG)
                }catch (e : Throwable){
                    e.printStackTrace()
                }
            }
        }
        fun start(){
            thread = Thread(this).apply {
                start()
            }
        }
        fun end(){
            run = false
            thread?.interrupt()
        }
    }
    private fun startConsoleConnect(){
        synchronized(this){
            if(consoleConnect==null){
                consoleConnect = ConsoleConnect(this).apply {
                    start()
                }
            }
        }
    }
    private fun stopConsoleConnect(){
        synchronized(this) {
            consoleConnect?.apply {
                end()
                consoleConnect = null
            }
        }
    }
    override fun onDestroyView() {
        stopConsoleConnect()
        running = false
        AppContext.screenBroadcastReceiverCallback = null
        super.onDestroyView()
    }
    override fun onResume() {
        startConsoleConnect()
        super.onResume()
    }
    private lateinit var tv: FixedTextView
    private lateinit var btn: Button
    private lateinit var scrollView: ScrollView
    private lateinit var root:View
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        AppContext.screenBroadcastReceiverCallback = fun(action){
            if (Intent.ACTION_SCREEN_ON == action) {//屏幕开屏
                startConsoleConnect()
            } else if (Intent.ACTION_SCREEN_OFF == action) {//屏幕关屏
                stopConsoleConnect()
            }
        }
        running = true
        autoRoll = true
        root = inflater.inflate(R.layout.fragment_console, container, false)
        scrollView = root.findViewById(R.id.scrollView)
        tv = root.findViewById(R.id.headLittleTextLine)
        btn = root.findViewById(R.id.lockRoll)
        btn.setOnClickListener{
            autoRoll=!autoRoll
            if(autoRoll){
                btn.text = "点击禁止滚动"
            }else{
                btn.text = "点击允许滚动"
            }
        }
        return root
    }
}