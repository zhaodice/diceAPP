package org.mirai.zhao.dice.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import org.json.JSONArray
import org.json.JSONObject
import org.mirai.zhao.dice.AccountsManager
import org.mirai.zhao.dice.R
import org.mirai.zhao.dice.component.FixedTextView
import org.mirai.zhao.dice.file.ConfigsIndexReader

class ActivityEditor : Activity() {
    companion object{
        val jsonConfigOperator
            get() = AccountsManager.JsonConfigOperator
        fun showEditor(context:Context,editKey:String){
            val intent = Intent(context,ActivityEditor::class.java)
            intent.putExtra("editKey",editKey)
            context.startActivity(intent)
        }
    }
    private var currentEditKey: String = ""
        set(value){
            val keys=configIndexData.keys()
            while(keys.hasNext()){
                val array = configIndexData.getJSONArray(keys.next())
                val size = array.length()
                for(i in 0 until size){
                    val array2=array.getJSONArray(i)
                    if(array2.getString(0) == value){
                        currentEditItem = array2
                    }
                }
            }
            field = value
            edited = false
        }
    private lateinit var currentEditItem : JSONArray//当前可编辑索引数据集
    private lateinit var allEditableKeys: ArrayList<String>//所有可编辑项目
    private lateinit var configIndexData: JSONObject//所有可编辑索引数据集
    private lateinit var currentID:String
    private lateinit var editNoticeTitle: FixedTextView
    private lateinit var editNoticeContent: FixedTextView
    private lateinit var edtOrderNoteText: EditText
    private lateinit var saveAndUpper: Button
    private lateinit var saveAndNext: Button
    private lateinit var saveAndExit: Button
    private var edited = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        val qq = AccountsManager.getCurrentEditQQ()
        if(qq==null){
            Toast.makeText(this,"请登陆并选择账号后再编辑信息。",Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        currentID = qq
        configIndexData = ConfigsIndexReader.read(this)
        configIndexData.apply {
            allEditableKeys = ArrayList()
            val keys=keys()
            while(keys.hasNext()){
                val array = getJSONArray(keys.next())
                val size = array.length()
                for(i in 0 until size) {
                    allEditableKeys.add(array.getJSONArray(i).getString(0))
                }
            }
        }

        currentEditKey = intent.getStringExtra("editKey")?:""
        if(currentEditKey.isEmpty()) {
            finish()
            return
        }

        editNoticeTitle = findViewById(R.id.edit_notice_title)
        editNoticeContent = findViewById(R.id.edit_notice_content)
        edtOrderNoteText = findViewById(R.id.edt_order_note_text)
        saveAndUpper = findViewById(R.id.saveAndUpper)
        saveAndNext = findViewById(R.id.saveAndNext)
        saveAndExit = findViewById(R.id.saveAndExit)
        edtOrderNoteText.addTextChangedListener {
            edited = true
        }
        saveAndUpper.setOnClickListener {
            val upIndex = getCurrentEditIndex()-1
            if(upIndex>=0){
                //切换到上一个
                save()
                currentEditKey = allEditableKeys[upIndex]
                initView()
            }
        }
        saveAndNext.setOnClickListener {
            val upIndex = getCurrentEditIndex()+1
            if(upIndex<allEditableKeys.size){
                //切换到下一个
                save()
                currentEditKey = allEditableKeys[upIndex]
                initView()
            }
        }
        saveAndExit.setOnClickListener {
            save()
            finish()
        }
        initView()
    }
    private fun getCurrentEditIndex():Int{
        return allEditableKeys.indexOf(currentEditKey)
    }
    private fun save(){
        if(edited && this::currentID.isInitialized){
            val content = edtOrderNoteText.text.toString()
            jsonConfigOperator.saveGlobalInfo(currentID,currentEditKey,content)
            edited=false
        }
    }
    private fun initView(){
        editNoticeTitle.text = currentEditItem.getString(1)
        editNoticeContent.text = currentEditItem.getString(2)
        edtOrderNoteText.setText(jsonConfigOperator.getGlobalInfo(currentID,currentEditKey))
    }

    override fun onBackPressed() {
        save()
        super.onBackPressed()
    }
}