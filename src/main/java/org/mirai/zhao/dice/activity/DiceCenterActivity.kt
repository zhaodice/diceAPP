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
                    .setTitle("?????????????????????")
                    .setMessage("GDICE X4 ??????????????????????????????CPU?????????????????????????????????\n????????? https://trpgbot.com/ ?????? DICE X4 ???????????????????????????????????????\n" +
                            "QQ??????882167580 978563358 1006272130")
                    .setPositiveButton("??????",null)
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
            AlertDialog.Builder(this).setTitle("??????/??????").setMessage("???????????????????????????????????????????????????????????????")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("??????") { _, _ -> // ??????????????????????????????
                        DiceServiceManager.restartService()
                    }
                    .setNeutralButton("????????????"){ _, _ -> // ??????????????????????????????
                        DiceServiceManager.completelyEndService{
                            exitProcess(0)
                        }
                    }
                    .setNegativeButton("??????") { _, _ ->
                        // ??????????????????????????????,?????????????????????????????????
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
            //??????????????????
            val inputServer = EditText(this)
            val textView = FixedTextView(this).apply {
                text = "???????????????????????????Mirai????????????????????????????????????????????????????????????????????????????????????"
            }
            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                addView(textView)
                addView(inputServer)
            }
            val builder = AlertDialog.Builder(this)
            builder.setTitle("??????????????????").setIcon(android.R.drawable.ic_dialog_info).setView(layout)
                    .setNegativeButton("??????", null)
            builder.setPositiveButton("??????") { _, _ ->
                Thread{
                    val id = inputServer.text.toString()
                    val url = WebUtil.getWebinfo("https://kv.trpgbot.com/?download=slider_url_$id")
                    runOnUiThread {
                        if(url.isNullOrEmpty() || try{URL(url)}catch (e:Throwable){null} ==null ){
                            Snackbar.make(navView, "???????????????", Snackbar.LENGTH_LONG).setAction("Action", null).show()
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
                                                "????????????????????????????????????????????????????????????"
                                            }else{
                                                 "??????????????????????????????????????????"
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
        //???????????????????????????,???????????????
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
                }?:"????????????"
                Handler(Looper.getMainLooper()).post {
                    headLittleTextLine.text = "$nick($qq) ????????????"
                }
            }.start()
        }else{//??????????????????????????????
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
        alterDialog.setTitle("??????")
        alterDialog.setMessage("??????????????????????????????????????????????????????????????????????????????????????????")
        alterDialog.setPositiveButton("????????????") { dialogInterface, _ ->
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