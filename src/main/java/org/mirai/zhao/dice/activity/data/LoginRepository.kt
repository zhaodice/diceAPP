package org.mirai.zhao.dice.activity.data

import android.app.AlertDialog
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.IConsoleService
import org.mirai.zhao.dice.ILoginCallbak
import org.mirai.zhao.dice.data.LoginProtocolType
import java.io.File

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository {
    fun login(qq: String, password: String,setProtocol: LoginProtocolType): String {
        // handle login
        try{
            val iConnect = (AppContext.context as AppContext).connectConsoleService()
            val result= CompletableDeferred<String>()
            (iConnect.iConsoleService as IConsoleService).loginBot(qq,password,setProtocol.name,object :ILoginCallbak.Stub(){
                override fun loginSuccess(botId: Long) {
                    AccountsManager.addAccount(botId.toString(),password)
                    result.complete("")
                }
                override fun loginFailure(reason: String) {
                    result.complete(reason)
                }
            })
            return runBlocking {
                return@runBlocking result.await()
            }
        }catch (e:Throwable){
            File(AppContext.miraiDir+"/LoginFailure.log").writeText(e.stackTraceToString())
            return "与mirai模块通讯失败，请将此反馈问题给开发者。错误信息：${e.message}\n请查看 LoginFailure.log 文件获取详细信息"
        }
    }
}