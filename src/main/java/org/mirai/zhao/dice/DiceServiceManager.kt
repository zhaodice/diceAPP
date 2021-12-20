package org.mirai.zhao.dice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import org.mirai.zhao.dice.console.MiraiCoreConsoleService
import kotlin.system.exitProcess

//独立服务进程管理器
object DiceServiceManager {
    var restarting=false
    private var servicePreserver = false
        set(value){
        if(value){
            field = true
            ConsoleServiceGuard.start()
        }else{
            field = false
            ConsoleServiceGuard.stop()
        }
    }
    private val appContext get() = AppContext.context as AppContext
    @Suppress("DEPRECATION")
    fun isServiceRunning(serviceClassName: String): Boolean {
        val activityManager = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
            if (runningServiceInfo.service.className == serviceClassName) {
                return true
            }
        }
        return false
    }
    private fun startBotService() {
        appContext.startService(Intent(appContext, MiraiCoreConsoleService::class.java).apply {
            putExtra("action", MiraiCoreConsoleService.START_SERVICE)
        })
    }
    private fun stopBotService() {
        appContext.startService(Intent(appContext, MiraiCoreConsoleService::class.java).apply {
            putExtra("action", MiraiCoreConsoleService.STOP_SERVICE)
        })
        appContext.stopService(Intent(appContext, MiraiCoreConsoleService::class.java))
    }

    private fun initServicePreserver(){
        if(!servicePreserver){
            servicePreserver = true
        }
    }
    private fun endServicePreserver(){
        if(servicePreserver){
            servicePreserver = false
        }
    }
    /*启动 mirai service*/
    fun startService(){
        if(MiraiCoreConsoleService.isIndividualProcess){
            exitProcess(0)
        }
        synchronized(DiceServiceManager::class){
            initServicePreserver()
            if (!isServiceRunning(MiraiCoreConsoleService::class.java.name)) {
                startBotService()
            }
        }
    }
    /*重启 / 启动 mirai service*/
    fun restartService(){
        if(MiraiCoreConsoleService.isIndividualProcess){
            exitProcess(0)
        }
        if(restarting) {
            return
        }
        println("正在启动/重启mirai...")
        Thread {
            synchronized(DiceServiceManager::class){
                restarting = true
                endServicePreserver()
                if(isServiceRunning(MiraiCoreConsoleService::class.java.name)) {
                    stopBotService()
                    Thread.sleep(1000)
                }
                startBotService()
                initServicePreserver()
                Thread.sleep(5000)
                restarting = false
            }
        }.start()
    }
    /*彻底结束service*/
    fun completelyEndService(callback:()->Unit){
        Thread {
            endServicePreserver()
            stopBotService()
            Thread.sleep(1000)
            callback()
        }.start()
    }
}