package org.mirai.zhao.dice.console

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.MiraiConsoleImplementation.Companion.start
import net.mamoe.mirai.network.LoginFailedException
import net.mamoe.mirai.utils.BotConfiguration
import org.mirai.zhao.dice.*
import org.mirai.zhao.dice.console.lower.AndroidMiraiConsole
import org.mirai.zhao.dice.console.lower.BufferedOutputStream
import org.mirai.zhao.dice.file.Assets
import org.mirai.zhao.dice.file.FileService
import org.mirai.zhao.dice.web.UpdateService
import java.io.File
import java.io.PrintStream
import kotlin.system.exitProcess
import kotlinx.coroutines.runBlocking
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.mirai.zhao.dice.console.util.AndroidLoginSolver
import org.mirai.zhao.dice.console.util.AndroidMiraiLogger
import org.mirai.zhao.dice.console.util.BotsConnectManager
import org.mirai.zhao.dice.console.util.SilkConverterImpl
import org.mirai.zhao.dice.data.LoginProtocolType
import java.security.Security

/*独立进程*/
class MiraiCoreConsoleService : Service() {
    private lateinit var consoleFrontEnd: AndroidMiraiConsole
    private var start = false
    private val iConsoleServiceStub = object : IConsoleService.Stub() {
        private var loginBot: Bot?=null
        private val callbacks:HashMap<String, ILogCallback> = HashMap()
        fun log(str: String){
            for((_, v) in callbacks){
                try{
                    v.logChanged(str)
                }catch (e: Throwable){

                }
            }
        }
        override fun getLogs(): String {
            return AndroidMiraiLogger.logStorage.build()
        }
        override fun commitVerification(token: String) {
            ApplicationLoginSolver.setResult(token)
        }
        override fun getCaptchaData(): ByteArray {
            return ApplicationLoginSolver.getCaptchaImage()
        }
        override fun loginBot(qq: String, password: String, setProtocol: String, callback: ILoginCallbak) {
            val protocolType = when(LoginProtocolType.valueOf(setProtocol)){
                LoginProtocolType.ANDROID_PHONE->{
                    BotConfiguration.MiraiProtocol.ANDROID_PHONE
                }
                LoginProtocolType.ANDROID_WATCH->{
                    BotConfiguration.MiraiProtocol.ANDROID_WATCH
                }
                LoginProtocolType.ANDROID_PAD->{
                    BotConfiguration.MiraiProtocol.ANDROID_PAD
                }
                LoginProtocolType.MAC->{
                    BotConfiguration.MiraiProtocol.MACOS
                }
                LoginProtocolType.IPAD->{
                    BotConfiguration.MiraiProtocol.IPAD
                }
            }
            synchronized(this){
                runBlocking {
                    loginBot?.closeAndJoin()
                    loginBot = BotFactory.newBot(qq.toLong(), password) {
                        protocol = protocolType
                        deviceInfo = BotConfiguration.Default.deviceInfo
                        loginSolver = AndroidLoginSolver.apply {
                            context = this@MiraiCoreConsoleService
                        }
                        cacheDir = File(AppContext.zhaoDice + "/.MiraiCache_$protocol/$qq")
                    }.apply {
                        try {
                            login()
                            callback.loginSuccess(id)
                        } catch (e: LoginFailedException) {
                            if (e.message == null) {
                                callback.loginFailure("未知错误")
                            } else {
                                val msg = e.message.toString()
                                when {
                                    msg.contains("被暂时冻结") -> {
                                        callback.loginFailure("账号被封禁，请先手机QQ登陆进行解封后再试。")
                                    }
                                    msg.contains("帐号或密码错误，请重新输入") -> {
                                        callback.loginFailure("QQ号或密码验证失败。")
                                    }
                                    msg.contains("请更换网络环境或在常用设备上登录或稍后再试") -> {
                                        callback.loginFailure("被风控，请切换WIFI/流量，或者协议，或者稍后再试.")
                                        configuration.cacheDir.deleteRecursively()
                                    }
                                    else -> {
                                        callback.loginFailure(msg)
                                    }
                                }
                            }
                        }
                        loginBot = null
                    }
                }
            }
        }

        override fun registLogCallback(name: String, callback: ILogCallback) {
            callbacks[name] = callback
        }
        override fun unRegistLogCallback(name: String) {
            callbacks.remove(name)
        }
        override fun exitAccount(qq: String) {
            AccountsManager.deleteAccount(qq)
        }
    }
    companion object {
        private const val CORE_VERSION = "2.9.0-RC2"
        const val START_SERVICE = 0
        const val STOP_SERVICE = 1
        var isIndividualProcess = false //当前是否为Mirai独立进程（附属进程）
        private fun newPluginFile():File{
            return File(AppContext.pluginsDir, "mirai-zhao-m2.jar")
        }
        private fun oldPluginFile():File{
            return File(AppContext.pluginsDir, "mirai-zhao.jar")
        }
        fun initPlugin(context: Context):File{
            //删除旧插件
            oldPluginFile().delete()
            //插件只初始化一次
            val newJar = newPluginFile()
            val shareData = context.getSharedPreferences("app", MODE_PRIVATE)
            val jarMd5=shareData.getString("jar_md5", "")
            val assetsMd5=FileService.getFileMD5(context.assets.open(newJar.name))
            if(!newJar.exists()||!jarMd5.equals(assetsMd5, true)){
                Assets.copyAssets(context, AppContext.pluginsDir, newJar.name)
                shareData.edit().putString("jar_md5", assetsMd5).apply()
                println("Wrote dice plugin -> ${AppContext.pluginsDir}")
            }
            return newJar
        }
        fun bindControlService(context: Context): ControlServiceConnection {
            val serviceConnection = ControlServiceConnection()
            context.bindService(Intent(context, MiraiCoreConsoleService::class.java), serviceConnection, BIND_IMPORTANT)
            return serviceConnection
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return iConsoleServiceStub
    }
    override fun onCreate() {
        isIndividualProcess=true
        System.setProperty("mirai.slider.captcha.supported", "1")
        try {
            val clz: Class<*> = Class.forName("net.mamoe.mirai.console.internal.MiraiConsoleBuildConstants")
            val versionConstField = clz.getDeclaredField("version")
            versionConstField.isAccessible = true
            val clz2: Class<*> = Class.forName("net.mamoe.mirai.console.util.SemVersion")
            val m=clz2.getMethod("parse", String::class.java)
            versionConstField.set(null, m.invoke(null, CORE_VERSION))
        }catch (e: Throwable){
            e.printStackTrace()
        }
        try {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
            Security.addProvider(BouncyCastleProvider())
            SilkConverterImpl.init()
        }catch (e: Throwable){
            e.printStackTrace()
        }
        this.consoleFrontEnd = AndroidMiraiConsole(baseContext)
        BotsConnectManager.init()
        startConsole()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isIndividualProcess=true
        try {
            intent?.getIntExtra(
                "action",
                START_SERVICE
            ).let { action ->
                return when (action) {
                    START_SERVICE -> {
                        startConsole()
                        START_STICKY
                    }
                    STOP_SERVICE -> {
                        stopConsole()
                        START_NOT_STICKY
                    }
                    else->{
                        super.onStartCommand(intent, flags, startId)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun stopConsole() {
        if (!start) {
            exitProcess(0)
        }
        Thread{
            runBlocking {
                try{
                    MiraiConsole.job.cancelAndJoin()
                    Thread.sleep(1000)
                }catch (e: Throwable){

                }
                stopSelf()
                DiceServiceManager.restartService()
            }
        }.start()
    }

    @Suppress("DEPRECATION")
    private fun startConsole(){
        synchronized(this){
            if (!start) {
                Thread{
                    try {
                        //初始化赵骰插件
                        initPlugin(this)
                        if(!BuildConfig.DEBUG) {
                            UpdateService.autoUpdate(newPluginFile())
                        }
                        //初始化日志系统
                        System.setOut(
                            PrintStream(
                                BufferedOutputStream(
                                    logger = AndroidMiraiLogger.run { ({ line: String? -> info(line) }) }
                                ),
                                false,
                                "UTF-8"
                            )
                        )
                        System.setErr(
                            PrintStream(
                                BufferedOutputStream(
                                    logger = AndroidMiraiLogger.run { ({ line: String? -> warning(line) }) }
                                ),
                                false,
                                "UTF-8"
                            )
                        )
                        AndroidMiraiLogger.setLogCallback(fun(text){
                            iConsoleServiceStub.log(text)
                        })
                        AndroidMiraiLogger.logStorage.clear()
                        //启动mirai console
                        consoleFrontEnd.start()
                    }catch (e: Throwable){
                        e.printStackTrace()
                        try{
                            MiraiConsole.cancel()
                            Thread.sleep(2000)
                        }catch (e: Throwable){

                        }
                        DiceServiceManager.restartService()
                    }
                }.start()
                start = true
            }
        }
    }
}