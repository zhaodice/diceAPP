package org.mirai.zhao.dice.console.util

import android.os.Build
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.BuildConfig
import java.lang.NullPointerException
import java.net.Socket
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext

object BotsConnectManager {
    private const val TAG = "BotsConnectManager"
    private val accounts = Vector<Long>()
    init{
        runBlocking {
            GlobalEventChannel.subscribeAlways(BotOnlineEvent::class, EmptyCoroutineContext) {
                if(bot.isOnline) {
                    synchronized(BotsConnectManager::class) {
                        if (!accounts.contains(bot.id)) {
                            accounts.add(bot.id)
                        }
                    }
                }
            }
            GlobalEventChannel.subscribeAlways(BotOfflineEvent::class, EmptyCoroutineContext) {
                synchronized(BotsConnectManager::class) {
                    if (this is BotOfflineEvent.Force) {
                        accounts.remove(bot.id)
                    }
                }
            }
        }
        //监听账号删除事件，自动关闭bot连接
        AccountsManager.addOnAccountChangedListener(
            TAG,
            object : AccountsManager.OnAccountChangedListener {
                override fun onChanged(
                    id: String?,
                    event: AccountsManager.AccountChangedEventType
                ) {
                    if (id != null) {
                        if (event == AccountsManager.AccountChangedEventType.ACCOUNT_DELETED) {
                            synchronized(BotsConnectManager::class) {
                                val idLong = id.toLong()
                                Bot.getInstanceOrNull(idLong)?.apply {
                                    close()
                                    configuration.cacheDir.deleteRecursively()
                                }
                                accounts.remove(idLong)
                            }
                        }
                    }
                }
            })
    }
    private val instance = Thread {
        Thread.sleep(5000)
        println("---- [NETWORK_DETECTOR] ----\n" +
                "We are aware that your system version is lower than Android 7.0\n" +
                "so we will use legacy-mode to help your upcoming reconnection.")
        while (true) {
            synchronized(BotsConnectManager::class) {
                runBlocking {
                    val iterator = accounts.iterator()
                    while (iterator.hasNext()) {
                        var netWorkDetector = false
                        val bot = Bot.getInstanceOrNull(iterator.next())
                        if (bot == null) {
                            iterator.remove()
                        }else{
                            if (!bot.isOnline) {
                                if (!netWorkDetector) {
                                    netWorkDetector = netWorkDetector()
                                    if(netWorkDetector){
                                        println("[NETWORK_DETECTOR] Network connected")
                                    }
                                }
                                if (netWorkDetector) {
                                    try {
                                        bot.login()
                                    } catch (e: Throwable) {
                                        if (e !is NullPointerException) {
                                            bot.closeAndJoin()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Thread.sleep(1000)
        }
    }
    private fun netWorkDetector():Boolean{
        var result = false
        try{
            val socket=Socket("msfwifi.3g.qq.com",8080)
            if(socket.isConnected){
                result = true
            }
            socket.close()
        }catch (e:Throwable){

        }
        return result
    }
    fun init(){
        if(BuildConfig.VERSION_CODE >=10) {// const val V2_7_0 = 10
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {//安卓6.0及其以下使用辅助重连(Mirai 2.7.0编译目标在安卓7.0，低版本不支持重连)
                if (!instance.isAlive) {
                    instance.isDaemon = true
                    instance.start()
                }
            }
        }
    }
}