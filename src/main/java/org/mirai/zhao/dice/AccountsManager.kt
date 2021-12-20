package org.mirai.zhao.dice

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import org.mirai.zhao.dice.file.JsonConfigOperator
import org.mirai.zhao.dice.file.TextFileOperator
import java.io.File
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

object AccountsManager {
    var shareData: SharedPreferences? = null
    private val zhaoDiceCurrentData: String
        get(){
            val currentEditQQ = getCurrentEditQQ()
            if(currentEditQQ!=null) {
                val subDir = "${AppContext.zhaoDice}/cocdata_$currentEditQQ/data"
                if (File(subDir).isDirectory) {
                    return subDir
                }
            }
            return AppContext.zhaoDiceRootData
        }
    private val autoLoginFile: File by lazy { File(AppContext.autoLoginFile) }
    private val onAccountChangedListeners = HashMap<String,OnAccountChangedListener>()
    private var currentEditQQ:String? = null
        set(value){
            if(value!= field) {
                if (value != null) {
                    shareData?.edit()?.putString("selfuin", value)?.apply()
                }
                val bak = field
                field = value
                JsonConfigOperator = JsonConfigOperator(zhaoDiceCurrentData)
                onAccountChangedListeners.invoke(bak, AccountChangedEventType.ACCOUNT_MODIFY)
            }
        }
    val AccountListData : Vector<String> by lazy { getAccountsList() }
    var JsonConfigOperator:JsonConfigOperator = JsonConfigOperator(zhaoDiceCurrentData)
    enum class AccountChangedEventType{
        ACCOUNT_DELETED,//账号被删除
        ACCOUNT_ADDED,//账号被添加
        ACCOUNT_MODIFY,//账号被切换
        ACCOUNT_OTHER//其他变化
    }
    interface OnAccountChangedListener{
        fun onChanged(id:String?,event:AccountChangedEventType)// id 改变之前的原号码 event 事件
    }
    fun addOnAccountChangedListener(token:String,onAccountChangedListener:OnAccountChangedListener){
        onAccountChangedListeners[token] = onAccountChangedListener
    }
    fun removeAccountChangedListener(token:String){
        onAccountChangedListeners.remove(token)
    }
    fun invokeOtherAccountEvent(){
        if(currentEditQQ==null){
            val accounts=getAccountsList()
            if(accounts.size>0){
                currentEditQQ = accounts[0]
            }
        }
        onAccountChangedListeners.invoke(null,AccountChangedEventType.ACCOUNT_OTHER)
    }
    fun setContext(context:Context){
        shareData = context.getSharedPreferences("app",MODE_PRIVATE)
        val accounts=getAccountsList()
        val storageID=(shareData as SharedPreferences).getString("selfuin",null)
        if(storageID==null){
            if(accounts.size>0){
                currentEditQQ = accounts[0]
            }
        }else{
            if(accounts.contains(storageID)) {
                currentEditQQ = storageID
            }else{
                if(accounts.size>0){
                    currentEditQQ = accounts[0]
                }
            }
        }
    }
    fun getCurrentEditQQ():String?{
        return currentEditQQ
    }
    private fun HashMap<String,OnAccountChangedListener>.invoke(id:String?,type:AccountChangedEventType) {
        Handler(Looper.getMainLooper()).post {
            forEach {
                it.value.onChanged(id, type)
            }
        }
    }
    fun deleteAccount(qq:String){
        val arrayList= StringBuilder()
        val accountsText= TextFileOperator.read(autoLoginFile)
        val accounts = accountsText.split("\n")
        for (account in accounts) {
            val accountInfo = account.split(" ")
            if (accountInfo.size >= 2&& accountInfo[0] != qq) {
                arrayList.append(account)
                arrayList.append("\n")
            }
        }
        TextFileOperator.write(autoLoginFile,arrayList.toString())
        AccountListData.remove(qq)
        if(qq == currentEditQQ){
            val list = getAccountsList()
            currentEditQQ = if(list.size==0){
                null
            }else{
                list[0]
            }
        }
        onAccountChangedListeners.invoke(qq,AccountChangedEventType.ACCOUNT_DELETED)
    }
    /*
   * 设置自动登陆
   * */
    fun addAccount(qq:String, password:String){
        if(thisAccountHasAutoLogin(qq))
            deleteAccount(qq)//先删账号
        val accountsText = StringBuilder(TextFileOperator.read(autoLoginFile))
        if(!accountsText.endsWith('\n'))
            accountsText.append('\n')
        val addPassword = if(password.isEmpty()){
            "#"
        }else{
            password
        }
        accountsText.append(String.format("%s %s\n", qq, addPassword))
        TextFileOperator.write(autoLoginFile, accountsText.toString())
        AccountListData.add(qq)
        currentEditQQ = qq//登陆成功后自动设置当前编辑的QQ
        onAccountChangedListeners.invoke(qq,AccountChangedEventType.ACCOUNT_ADDED)
    }
    private fun getAccountsList(): Vector<String> {
        val arrayList=Vector<String>()
        val accountsText= try {
            TextFileOperator.read(autoLoginFile)
        } catch(e:Throwable){
            return arrayList
        }
        val accounts = accountsText.split("\n")
        for (element in accounts) {
            val accountInfo = element.split(" ")
            if (accountInfo.size >= 2) {
                arrayList.add(accountInfo[0])
            }
        }
        return arrayList
    }
    /*
   * 账号是否已经有自动登陆了
   * */
    private fun thisAccountHasAutoLogin(qq:String) : Boolean{
        val accountsText= TextFileOperator.read(autoLoginFile)
        val accounts = accountsText.split("\n")
        for (element in accounts) {
            val accountInfo = element.split(" ")
            if (accountInfo.size >= 2) {
                if(qq == accountInfo[0]){
                    return true
                }
            }
        }
        return false
    }
    /*private fun showNotice(text:String, context: View){
        Snackbar.make(context, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }*/
    fun showAccountSwitcher(v:View){
        val qqArray = getAccountsList()
        if(qqArray.size>1) {
            val view = android.R.style.Theme_Material_Light_Dialog
            val builder = AlertDialog.Builder(v.context, view)
            builder.setTitle("请选择已经登陆的骰娘QQ号")
            builder.setItems(Array(qqArray.size) {
                qqArray[it]
            }) { _, which ->
                currentEditQQ = qqArray[which]
            }
            builder.show()
        }else{
            Snackbar.make(v, "只有账号数量>=2时才可以切换哦~", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
    fun deleteAccountsCache(qq:String=""){
        File(AppContext.zhaoDice+"/.MiraiCache_ANDROID_PHONE/$qq").deleteRecursively()
        File(AppContext.zhaoDice+"/.MiraiCache_ANDROID_PAD/$qq").deleteRecursively()
        File(AppContext.zhaoDice+"/.MiraiCache_ANDROID_WATCH/$qq").deleteRecursively()
    }
}