package org.mirai.zhao.dice.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.IConsoleService
import org.mirai.zhao.dice.R

class CaptchaActivity : AppCompatActivity() {
    private lateinit var iConsoleService: IConsoleService
    private lateinit var captchaConfirmBtn: Button
    private lateinit var captchaInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captcha)
        captchaConfirmBtn=findViewById(R.id.captchaConfirm_btn)
        captchaInput=findViewById(R.id.captcha_input)
        val captchaView = findViewById<ImageView>(R.id.captcha_view)
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
        captchaConfirmBtn.setOnClickListener{
            val answer=captchaInput.text.toString()
            if(answer.isNotEmpty()) {
                iConsoleService.commitVerification(answer)//继续登陆
                finish()
            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}