package org.mirai.zhao.dice.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import fightcent.permissionrequest.PermissionRequest
import fightcent.permissionrequest.PermissionRequestListener
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.mirai.zhao.dice.*
import org.mirai.zhao.dice.activity.ui.login.LoginActivity
import org.mirai.zhao.dice.component.FixedTextView
import org.mirai.zhao.dice.web.HttpHelper
import org.mirai.zhao.dice.web.WebUtil
import java.io.File
import java.net.URL
import java.nio.charset.Charset
import kotlin.system.exitProcess


class DiceCenterActivity : AppCompatActivity() {
    companion object{
        const val TAG = "DiceCenterActivity"
        fun circleBitmap(source: Bitmap): Bitmap? {
            val width = source.width
            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.isAntiAlias = true
            canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(source, 0f, 0f, paint)
            return bitmap
        }
    }
    private lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var headerView:View
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_center)
        if(AppContext.isMiraiGo){
            if(Build.SUPPORTED_ABIS.contains("x86") || Build.SUPPORTED_ABIS.contains("x86_64")){
                AlertDialog.Builder(this)
                    .setTitle("系统结构不支援")
                    .setMessage("GDICE X4 有可能不支援该系统的CPU架构（你是模拟器吗？）\n请访问 https://trpgbot.com/ 下载 DICE X4 版，详情请加群取得技术支援\n" +
                            "QQ群：882167580 978563358 1006272130")
                    .setPositiveButton("确定",null)
                    .show()
            }
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_console, R.id.nav_setting, R.id.nav_login), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.findViewById<ImageView>(R.id.nav_exit).setOnClickListener{
            AlertDialog.Builder(this).setTitle("重启/退出").setMessage("是重启还是退出程序？退出程序后骰娘将下线。")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("重启") { _, _ -> // 点击“确认”后的操作
                        DiceServiceManager.restartService()
                    }
                    .setNeutralButton("彻底退出"){ _, _ -> // 点击“确认”后的操作
                        DiceServiceManager.completelyEndService{
                            exitProcess(0)
                        }
                    }
                    .setNegativeButton("取消") { _, _ ->
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }.show()
        }
        headerView=navView.getHeaderView(0)
        headerView.findViewById<ImageView>(R.id.imageView).apply {
            setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
            }
            setOnLongClickListener {
                AccountsManager.showAccountSwitcher(navView)
                true
            }
        }
        findViewById<Button>(R.id.headAccountSwitch).setOnClickListener {
            AccountsManager.showAccountSwitcher(navView)
        }
        findViewById<Button>(R.id.sliderHelper).setOnClickListener {
            //滑块助手按钮
            val inputServer = EditText(this)
            val textView = FixedTextView(this).apply {
                text = "该功能仅用于电脑版Mirai赵骰的滑块辅助验证，请输入电脑上的软件提示的验证码继续。"
            }
            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                addView(textView)
                addView(inputServer)
            }
            val builder = AlertDialog.Builder(this)
            builder.setTitle("请输入验证码").setIcon(android.R.drawable.ic_dialog_info).setView(layout)
                    .setNegativeButton("取消", null)
            builder.setPositiveButton("确认") { _, _ ->
                Thread{
                    val id = inputServer.text.toString()
                    val url = WebUtil.getWebinfo("https://kv.trpgbot.com/?download=slider_url_$id")
                    runOnUiThread {
                        if(url.isNullOrEmpty() || try{URL(url)}catch (e:Throwable){null} ==null ){
                            Snackbar.make(navView, "验证码无效", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                        }else{
                            ApplicationLoginSolver.resetResult()
                            UnsafeLoginActivity.startActivity(url,this)
                            Thread {
                                runBlocking {
                                    val result = ApplicationLoginSolver.awaitResult()
                                    if(result.isNotEmpty()){
                                        val uploadResult = HttpHelper.postData("https://kv.trpgbot.com/?upload=slider_answer_$id&expiredTime=600000",result.toByteArray(),null)
                                        runOnUiThread {
                                            Snackbar.make(navView, if(uploadResult=="1"){
                                                "验证码提交成功！请在电脑版继续你的操作。"
                                            }else{
                                                 "系统繁忙，请稍后或明天再试。"
                                                 }, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                                        }
                                    }
                                }
                            }.start()
                        }
                    }
                }.start()
            }
            builder.show()
        }
        AccountsManager.addOnAccountChangedListener(TAG, object : AccountsManager.OnAccountChangedListener {
            override fun onChanged(id: String?, event: AccountsManager.AccountChangedEventType) {
                freshPortrait(AccountsManager.getCurrentEditQQ())
            }
        })
        freshPortrait(AccountsManager.getCurrentEditQQ())
        //检测是否有读写权限,没有则申请
        try{
            val file= File(AppContext.miraiDir, "check")
            file.parentFile?.mkdirs()
            if(file.exists()||file.createNewFile()){
                DiceServiceManager.startService()
                file.delete()
            }else{
                doRequirePermission()
            }
        }catch (e: Throwable){
            doRequirePermission()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.dice_center, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")

    fun freshPortrait(qq: String?){
        findViewById<Button>(R.id.headAccountSwitch).visibility = if(AccountsManager.AccountListData.size <= 1){
            View.GONE
        }else{
            View.VISIBLE
        }
        val mImageView = headerView.findViewById<ImageView>(R.id.imageView)
        val headLittleTextLine = headerView.findViewById<FixedTextView>(R.id.headLittleTextLine)
        if(qq!=null) {
            Thread {
                try {
                    val url = URL("http://q2.qlogo.cn/headimg_dl?dst_uin=$qq&spec=100")
                    val bitmap = circleBitmap(BitmapFactory.decodeStream(url.openStream()))
                    Handler(Looper.getMainLooper()).post {
                        mImageView.setImageBitmap(bitmap)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                val nick = try {
                    val text = URL("https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=$qq").readText(Charset.forName("GB2312"))
                    if (text.startsWith("portraitCallBack(")) {
                        JSONObject(text.substring(17, text.length - 1)).getJSONArray(qq).getString(6)
                    }else {
                        null
                    }
                }catch (e: Throwable){
                    null
                }?:"未知昵称"
                Handler(Looper.getMainLooper()).post {
                    headLittleTextLine.text = "$nick($qq) 已选中√"
                }
            }.start()
        }else{//无账号，设为默认头像
            mImageView.setImageResource(R.mipmap.ic_launcher_foreground)
            headLittleTextLine.text = getString(R.string.nav_header_subtitle)
        }
    }
    private fun doRequirePermission() {
        PermissionRequest(this).request(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), object : PermissionRequestListener {
            private fun noPermission() {
                showAlterDialog()
            }

            override fun onAllowAllPermissions() {
                DiceServiceManager.startService()
                AccountsManager.invokeOtherAccountEvent()
            }

            override fun onDenySomePermissions(denyPermissions: Collection<String>) {
                noPermission()
            }

            override fun onDenyAndNeverAskAgainSomePermissions(denyAndNeverAskAgainPermissions: Collection<String>) {
                noPermission()
            }
        })
    }
    private fun showAlterDialog() {
        val alterDialog = AlertDialog.Builder(this)
        alterDialog.setTitle("错误")
        alterDialog.setMessage("没有存储权限，程序不能正常管理赵骰的数据文件，请授权后再试。")
        alterDialog.setPositiveButton("重新授权") { dialogInterface, _ ->
            doRequirePermission()
            dialogInterface.cancel()}
        alterDialog.create()
        alterDialog.show()
    }

    override fun onDestroy() {
        AccountsManager.removeAccountChangedListener(TAG)
        super.onDestroy()
    }
}