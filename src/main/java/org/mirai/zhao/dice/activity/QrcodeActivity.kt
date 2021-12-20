package org.mirai.zhao.dice.activity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageView
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.IConsoleService
import org.mirai.zhao.dice.R

class QrcodeActivity : AppCompatActivity() {
    private lateinit var iConsoleService: IConsoleService
    private lateinit var qrCodeConfirmBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        qrCodeConfirmBtn = findViewById(R.id.QrConfirm_btn)
        val captchaView = findViewById<ImageView>(R.id.qrcodeImage)
        (AppContext.context as AppContext).connectConsoleService().ifDisconnect {
            finish()
        }.iConsoleService.apply {
            if(this==null){
                finish()
            }else{
                iConsoleService = this
            }
        }
        val data = iConsoleService.captchaData
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        captchaView.setImageBitmap(bitmap)
        qrCodeConfirmBtn.setOnClickListener{
            iConsoleService.commitVerification("scanned")//继续登陆
            finish()
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        iConsoleService.commitVerification("")//取消验证
        return super.onKeyDown(keyCode, event)
    }
}