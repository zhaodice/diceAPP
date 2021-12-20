package org.mirai.zhao.dice.activity.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.R
import org.mirai.zhao.dice.activity.ActivityEditor
import org.mirai.zhao.dice.file.ConfigsIndexReader


class SettingsFragment : Fragment() {
    companion object {
        const val TAG = "SettingsFragment"
    }
    private lateinit var showGroups:Array<String>
    private lateinit var showChilds:Array<Array<String>>
    private lateinit var configIndexData:JSONObject
    private lateinit var switchOpenDice: SwitchCompat
    private lateinit var switchPublicMode: SwitchCompat
    private lateinit var switchKeyAutoReply: SwitchCompat
    private lateinit var switchSystemSplit: SwitchCompat
    private lateinit var expandListId: ExpandableListView
    private val switchCheckListener = View.OnClickListener {
        val storage = AccountsManager.JsonConfigOperator
        val qq = AccountsManager.getCurrentEditQQ()
        if(qq!=null) {
            storage.saveGlobalBoolean(qq, "IS_PUBLIC_DICE", switchPublicMode.isChecked)
            storage.saveGlobalBoolean(qq, "OPEN_IN_GLOBAL", switchOpenDice.isChecked)
            storage.saveGlobalBoolean(qq, "KEY_AUTO_REPLY", switchKeyAutoReply.isChecked)
            storage.saveGlobalBoolean(qq,"DICE_SYSTEM_SPLIT_SWITCH",switchSystemSplit.isChecked)
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val qq = AccountsManager.getCurrentEditQQ()
        if(qq==null){
            root.isEnabled = false
            Toast.makeText(root.context, "未登陆，禁止使用！请点击头像登陆QQ后重新进入。", Toast.LENGTH_SHORT).show()
        }else{
            expandListId = root.findViewById(R.id.settingsList)
            switchOpenDice = root.findViewById(R.id.switch_openDice)
            switchPublicMode = root.findViewById(R.id.switch_publicMode)
            switchKeyAutoReply = root.findViewById(R.id.switch_keyAutoReply)
            switchSystemSplit = root.findViewById(R.id.switch_systemSplit)
            switchOpenDice.setOnClickListener(switchCheckListener)
            switchPublicMode.setOnClickListener(switchCheckListener)
            switchKeyAutoReply.setOnClickListener(switchCheckListener)
            switchSystemSplit.setOnClickListener(switchCheckListener)
            AccountsManager.addOnAccountChangedListener(TAG, object : AccountsManager.OnAccountChangedListener {
                override fun onChanged(id: String?, event: AccountsManager.AccountChangedEventType) {
                    freshCheckBoxes()
                }
            })
            val switchBootStart = root.findViewById<SwitchCompat>(R.id.switch_bootStart)
            switchBootStart.setOnCheckedChangeListener { _, b -> AccountsManager.shareData?.edit()?.putBoolean("bootStart", b)?.apply() }
            switchBootStart.isChecked = AccountsManager.shareData?.getBoolean("bootStart", true) ?: true
            val switchSilkEncoder = root.findViewById<SwitchCompat>(R.id.switch_silkEncoder)
            switchSilkEncoder.setOnCheckedChangeListener { _, b -> AccountsManager.shareData?.edit()?.putBoolean("silkEncoder", b)?.apply() }
            switchSilkEncoder.isChecked = AccountsManager.shareData?.getBoolean("silkEncoder", true) ?: true

            initView(root)
            freshCheckBoxes()
            if(!AppContext.hasIgnoreBatteryOptimization(root.context)){
                if(container==null) {
                    AppContext.ignoreBatteryOptimization(root.context)
                }else{
                    Thread{
                        val snackBar = Snackbar.make(container, "关闭电池优化，可以帮助APP更加稳定地在后台运行。", Snackbar.LENGTH_INDEFINITE).setAction("关闭优化") {
                            AppContext.ignoreBatteryOptimization(root.context)
                        }
                        snackBar.show()
                        Thread.sleep(5000)
                        snackBar.dismiss()
                    }.start()
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        AccountsManager.removeAccountChangedListener(TAG)
        super.onDestroyView()
    }
    private fun freshCheckBoxes(){
        val storage = AccountsManager.JsonConfigOperator
        val currentID = AccountsManager.getCurrentEditQQ()
        if(currentID!=null) {
            switchPublicMode.isChecked = storage.getGlobalBoolean(currentID, "IS_PUBLIC_DICE")
            switchOpenDice.isChecked = storage.getGlobalBoolean(currentID, "OPEN_IN_GLOBAL")
            switchKeyAutoReply.isChecked = storage.getGlobalBoolean(currentID, "KEY_AUTO_REPLY")
            switchSystemSplit.isChecked = storage.getGlobalBoolean(currentID,"DICE_SYSTEM_SPLIT_SWITCH",true)
        }
    }
    private fun initView(root: View) {
        initConfigOptions(root)
        val adapter = ExpandableListViewAdapter(showGroups, showChilds) //ExpandableListViewAdapter(root.context, showGroups, showChilds)
        expandListId.setAdapter(adapter)
        //默认展开第一个数组
        expandListId.expandGroup(0)
        //关闭数组某个数组，可以通过该属性来实现全部展开和只展开一个列表功能
        //expand_list_id.collapseGroup(0);
        /*expandListId.setOnGroupClickListener { expandableListView, view, groupPosition, l ->
            //println(groups.get(groupPosition))
            false
        }*/
        //子视图的点击事件
        expandListId.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            //println(childs.get(groupPosition).get(childPosition))
            ActivityEditor.showEditor(root.context, configIndexData.getJSONArray(showGroups[groupPosition]).getJSONArray(childPosition).getString(0))
            true
        }
        //用于当组项折叠时的通知。
        //expand_list_id.setOnGroupCollapseListener(OnGroupCollapseListener { groupPosition -> showToastShort("折叠了数据___" + groups.get(groupPosition)) })
        //
        //用于当组项折叠时的通知。
        //expand_list_id.setOnGroupExpandListener(OnGroupExpandListener { groupPosition -> showToastShort("展开了数据___" + groups.get(groupPosition)) })
    }
    private fun initConfigOptions(root: View){
        configIndexData = ConfigsIndexReader.read(root.context)
        val genGroups = ArrayList<String>()
        val genChildren = ArrayList<Array<String>>()
        val keys=configIndexData.keys()
        while(keys.hasNext()){
            val key=keys.next()
            genGroups.add(key)
            val jsonArray=configIndexData.getJSONArray(key)
            val genChild = Array<String>(jsonArray.length()){
                jsonArray.getJSONArray(it).getString(1)
            }
            genChildren.add(genChild)
        }
        showGroups = Array(genGroups.size){
            genGroups[it]
        }
        showChilds = Array(genChildren.size){
            genChildren[it]
        }
    }
}