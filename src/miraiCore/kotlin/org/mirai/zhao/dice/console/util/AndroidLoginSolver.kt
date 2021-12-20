package org.mirai.zhao.dice.console.util

import android.content.Intent
import net.mamoe.mirai.Bot
import net.mamoe.mirai.utils.LoginSolver
import org.mirai.zhao.dice.ApplicationLoginSolver
import org.mirai.zhao.dice.activity.CaptchaActivity
import org.mirai.zhao.dice.activity.UnsafeLoginActivity
import org.mirai.zhao.dice.console.MiraiCoreConsoleService


object AndroidLoginSolver : LoginSolver() {
    lateinit var context: MiraiCoreConsoleService
    private lateinit var url: String
    override suspend fun onSolvePicCaptcha(bot: Bot, data: ByteArray): String {
        ApplicationLoginSolver.resetResult()
        ApplicationLoginSolver.setCaptchaImage(data)
        val notifyIntent = Intent(context, CaptchaActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK// or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(notifyIntent)
        return ApplicationLoginSolver.awaitResult()
    }

    override suspend fun onSolveSliderCaptcha(bot: Bot, url: String): String {
        ApplicationLoginSolver.resetResult()
        AndroidLoginSolver.url = url
        sendVerifyNotification()
        val ticket = ApplicationLoginSolver.awaitResult()
        AndroidMiraiLogger.info("onSolveSliderCaptcha URL=$url ticket=$ticket")
        return ticket
    }

    override suspend fun onSolveUnsafeDeviceLoginVerify(bot: Bot, url: String): String {
        ApplicationLoginSolver.resetResult()
        AndroidLoginSolver.url = url
        sendVerifyNotification()
        return ApplicationLoginSolver.awaitResult()
    }

    private fun sendVerifyNotification() {
        UnsafeLoginActivity.startActivity(url, context)
    }
}