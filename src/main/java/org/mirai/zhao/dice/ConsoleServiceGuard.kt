package org.mirai.zhao.dice

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import org.mirai.zhao.dice.activity.DiceCenterActivity
import org.mirai.zhao.dice.console.MiraiCoreConsoleService

/*保活服务*/
class ConsoleServiceGuard: Service() {
    companion object{
        private const val START_SERVICE = 0
        private const val STOP_SERVICE = 1
        private const val notifyID="zhao_notification_id"
        private const val notifyName="zhao_notification_name"
        fun stop(){
            AppContext.context?.apply {
                startService(Intent(this, ConsoleServiceGuard::class.java).apply {
                    putExtra("action", STOP_SERVICE)
                })
            }
        }
        fun start(){
            AppContext.context?.apply {
                startService(Intent(this, ConsoleServiceGuard::class.java).apply {
                    putExtra("action", START_SERVICE)
                })
            }
        }
    }
    class ServicePreserver:Runnable{
        private lateinit var thread: Thread
        override fun run() {
            try{
                AppContext.context?.apply {
                    while (true){
                        var connectTimes = 0
                        val conn = connectConsoleService(this)
                        while (!(conn.mBound)) {
                            Thread.sleep(1000)
                            connectTimes++
                            if (connectTimes > 3) {
                                DiceServiceManager.restartService()
                                Thread.sleep(5000)
                                break
                            }
                        }
                        Thread.sleep(2000)
                    }
                }
            }catch (e: InterruptedException){

            }
        }
        fun start(){
            if(!this::thread.isInitialized) {
                thread = Thread(this)
                thread.start()
            }
        }
        fun end(){
            if(this::thread.isInitialized) {
                thread.interrupt()
            }
        }
    }
    private var servicePreserver: ServicePreserver? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @Suppress("DEPRECATION")
    override fun onCreate() {
        // 在API11之后构建Notification的方式
        val builder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Notification.Builder(this.applicationContext, notifyID)
        else
            Notification.Builder(this.applicationContext)
        val nfIntent = Intent(this, DiceCenterActivity::class.java)
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("Dice! 赵骰核心正在挂后台") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("赵骰核心正在运行中...") // 设置上下文内容
                .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
        // 【适配Android8.0】设置Notification的Channel_ID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notifyID)
        }
        val notification = builder.build() // 获取构建好的Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(notifyID, notifyName, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification) // 开始前台服务
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            intent?.getIntExtra(
                    "action",
                MiraiCoreConsoleService.START_SERVICE
            ).let { action ->
                return when (action) {
                    MiraiCoreConsoleService.START_SERVICE -> {
                        servicePreserver = ServicePreserver().apply {
                            start()
                        }
                        START_STICKY
                    }
                    MiraiCoreConsoleService.STOP_SERVICE -> {
                        servicePreserver?.end()
                        stopService(Intent(this, ConsoleServiceGuard::class.java))
                        START_NOT_STICKY
                    }
                    else->
                        super.onStartCommand(intent, flags, startId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onStartCommand(intent, flags, startId)
        //return super.onStartCommand(intent, flags, startId)
    }
}