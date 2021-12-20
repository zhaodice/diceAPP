package org.mirai.zhao.dice.activity.ui.accountManager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.mirai.zhao.dice.*
import org.mirai.zhao.dice.activity.ui.login.LoginActivity

class AccountManagerFragment : Fragment() {
    companion object{
        const val TAG = "AccountManagerFragment"
    }
    private lateinit var iConsoleService: IConsoleService
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_accounts, container, false)
        val accountList=root.findViewById<ListView>(R.id.accountList)
        val adapter = ArrayAdapter(root.context, android.R.layout.simple_list_item_1, AccountsManager.AccountListData) //新建并配置ArrayAapeter
        (AppContext.context as AppContext).connectConsoleService().ifDisconnect {
            accountList.isEnabled = false
        }.iConsoleService.apply {
            if(this==null){
                accountList.isEnabled = false
            }else{
                iConsoleService = this
            }
        }

        accountList.adapter = adapter
        accountList.setOnItemClickListener{ _, _, position, _ ->
            val qq= AccountsManager.AccountListData[position]
            AlertDialog.Builder(root.context).setTitle("请选择你的操作").setMessage(String.format("你要对账号 %s 做什么？",qq))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("退出账号") { _, _ ->
                    AccountsManager.deleteAccount(qq)
                    iConsoleService.exitAccount(qq)
                }.setNegativeButton("重新登录") { _, _ ->
                    AccountsManager.deleteAccountsCache(qq)
                    DiceServiceManager.restartService()
                }.setNeutralButton("取消") { _, _ -> }.show()
        }
        root.findViewById<FloatingActionButton>(R.id.addAccount).setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        AccountsManager.addOnAccountChangedListener(TAG,object:AccountsManager.OnAccountChangedListener{
            override fun onChanged(id: String?, event: AccountsManager.AccountChangedEventType) {
                if(event == AccountsManager.AccountChangedEventType.ACCOUNT_DELETED){
                    adapter.notifyDataSetChanged()
                }else if(event == AccountsManager.AccountChangedEventType.ACCOUNT_ADDED){
                    adapter.notifyDataSetChanged()
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        AccountsManager.removeAccountChangedListener(TAG)
        super.onDestroyView()
    }
}