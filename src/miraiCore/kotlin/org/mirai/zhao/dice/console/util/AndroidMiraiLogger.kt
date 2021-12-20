package org.mirai.zhao.dice.console.util

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.BuildConfig
import org.mirai.zhao.dice.DiceServiceManager
import org.mirai.zhao.dice.console.MiraiCoreConsoleService
import org.mirai.zhao.dice.data.LogBuilder
import org.mirai.zhao.dice.data.LogBuilder.Companion.LEVEL_DEBUG
import org.mirai.zhao.dice.data.LogBuilder.Companion.LEVEL_ERROR
import org.mirai.zhao.dice.data.LogBuilder.Companion.LEVEL_INFO
import org.mirai.zhao.dice.data.LogBuilder.Companion.LEVEL_WARNING
import java.util.*

object AndroidMiraiLogger : MiraiLoggerPlatformBase() {
    override val identity: String = "main"
    val logStorage= LogBuilder().limit(100)
    fun setLogCallback(callback:(String) -> Unit){
        logStorage.logCallback = callback
    }
    override fun debug0(message: String?, e: Throwable?) {
        if (message != null) {
            logStorage.append(LEVEL_DEBUG,message)
        }
    }
    override fun error0(message: String?, e: Throwable?) {
        if (message != null) {
            if(!BuildConfig.DEBUG){//正式版，应屏蔽一些类型的错误
                if(e!=null) {
                    if (e.javaClass.name == "net.mamoe.mirai.internal.network.components.ExceptionInPacketCodecException") {
                        return
                    }
                }
            }
            logStorage.append(LEVEL_ERROR, message)
            if (e != null) {
                logStorage.append(LEVEL_ERROR, e.stackTraceToString())
            }
        }
    }
    override fun info0(message: String?, e: Throwable?) {
        if (message != null) {
            if(!BuildConfig.DEBUG) {//正式版，应屏蔽一些类型的消息
                if(message.startsWith("[CDS]") || message.startsWith("close [socket]") || message.startsWith("[OkHttp]")) {//过滤一些无用log
                    return
                }
            }
            logStorage.append(LEVEL_INFO,message)
        }
    }
    override fun verbose0(message: String?, e: Throwable?) {
        if (message != null) {
            logStorage.append(LEVEL_INFO,message)
        }
    }
    override fun warning0(message: String?, e: Throwable?) {
        if (message != null) {
            if(!BuildConfig.DEBUG) {//正式版，应屏蔽一些类型的警告
                if (message == "No route to host (Mostly due to no Internet connection). Retrying in 3s...") {
                    return
                }else if(message.startsWith("SLF4J: ")){
                    return
                }else if(MiraiCoreConsoleService.isIndividualProcess) {//判断自身是否处于mirai系统内
                    //检测到是否需要特殊操作的特征log
                    if (message.matches(Regex("^Failed to find member \\d+ in group \\d+$"))) {
                        DiceServiceManager.restartService()
                    }else if(
                            message == "Init failed. Retrying in 3s... (rootCause=java.lang.IllegalStateException: network is dead therefore can't init.)" ||
                            message == "Login failed. Retrying in 3s... (rootCause=java.util.concurrent.CancellationException: bot is dead therefore can't send wtlogin.login)"
                    ){// end from 2.7.0
                        AccountsManager.deleteAccountsCache()
                        DiceServiceManager.restartService()
                    }
                }
            }
            logStorage.append(LEVEL_WARNING,message)
        }
    }
}