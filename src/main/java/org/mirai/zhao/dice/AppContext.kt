package org.mirai.zhao.dice

import android.annotation.SuppressLint
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment.getExternalStorageDirectory
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.Keep
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import org.mirai.zhao.dice.console.MiraiCoreConsoleService
import java.io.File
import android.content.pm.ApplicationInfo
import android.widget.Toast


class AppContext : Application() {
    private var controlServiceConnection: ControlServiceConnection?=null
    private class ScreenBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            screenBroadcastReceiverCallback?.let { intent.action?.let { it1 -> it(it1) } }
        }
    }
    @Suppress("DEPRECATION")
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        dataStorage = getExternalStorageDirectory().path
    }
    override fun onCreate() {
        context = this
        versionCode = getAppVersionCode(this)
        CustomActivityOnCrash.install(this)

        val appInfo: ApplicationInfo?
        try {
            appInfo = this.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            frameType = appInfo.metaData.getString("FrameType") as String
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        AccountsManager.setContext(this)
        registerReceiver(ScreenBroadcastReceiver(), IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_USER_PRESENT)
        })
        val f=File(miraiDir)
        if(!f.exists())
            f.mkdir()
        System.setProperty("zhao.dice.plugins.dir", pluginsDir)
        if(BuildConfig.DEBUG) {
            System.setProperty("kotlinx.coroutines.debug", "on")
        }
        super.onCreate()
    }
    fun connectConsoleService(context: Context = this@AppContext): ControlServiceConnection {
        controlServiceConnection.apply {
            return if(this==null || !this.mBound){
                val connection = MiraiCoreConsoleService.bindControlService(context)
                controlServiceConnection = connection
                connection
            }else{
                this
            }
        }
    }
    companion object {
        var screenBroadcastReceiverCallback:((action: String)->Unit)?=null
        @JvmField
        @Keep
        var versionCode:Long = 0
        var dataStorage = ""
        var frameType = ""
        val isMiraiGo get() = frameType=="MiraiGo"
        val isMiraiCore get() = frameType=="MiraiCore"
        var context: AppContext? = null
        val pluginsDir: String
            get() = if(context!=null)
            context!!.getDir("plugins", 0).absolutePath
        else
            ""
        val miraiDir get() = "$dataStorage/MiraiDice"
        val zhaoDice get() = "$miraiDir/data/ZhaoDice"
        val zhaoDiceRootData get() = "$zhaoDice/cocdata/data"
        val autoLoginFile: String
            get()= if(isMiraiCore){
            "$zhaoDiceRootData/autoLogin.txt"
        }else{
            context!!.filesDir.resolve("autoLogin.txt").absolutePath
        }
        fun hasIgnoreBatteryOptimization(activity: Context):Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val powerManager = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
                //  判断当前APP是否有加入电池优化的白名单
                return powerManager.isIgnoringBatteryOptimizations(activity.packageName)
            }
            return true
        }
        @SuppressLint("BatteryLife")
        fun ignoreBatteryOptimization(activity: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!hasIgnoreBatteryOptimization(activity)) {//如果没有加入电池优化的白名单，弹出设置对话框。
                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    intent.data = Uri.parse("package:" + activity.packageName)
                    activity.startActivity(intent)
                }
            }
        }
        @Suppress("DEPRECATION")
        fun getAppVersionCode(context: Context): Long {
            var appVersionCode: Long = 0
            try {
                val packageInfo = context.applicationContext
                        .packageManager
                        .getPackageInfo(context.packageName, 0)
                appVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    packageInfo.versionCode.toLong()
                }
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return appVersionCode
        }
    }
}