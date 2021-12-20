package org.mirai.zhao.dice.activity.ui.login

import android.app.Activity
import android.os.*
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.mirai.zhao.dice.*
import org.mirai.zhao.dice.data.LoginProtocolType
import org.mirai.zhao.dice.component.FixedTextView

class LoginActivity : AppCompatActivity() {
    companion object{
        const val TAG = "LoginActivity"
    }
    private val scanQrcodeLoginMode = AppContext.isMiraiGo//MiraiGo使用扫码方式登录，无需密码
    private lateinit var iConsoleService: IConsoleService
    private lateinit var loginProcessPrint: FixedTextView
    private lateinit var loginViewModel: LoginViewModel
    private var loginProtocol: LoginProtocolType = LoginProtocolType.ANDROID_PAD
    private lateinit var cancelThread:Thread
    override fun onStop() {
        unRegisterLogToiService()
        super.onStop()
    }
    private fun connectConsoleService(){
        (AppContext.context as AppContext).connectConsoleService().iConsoleService.apply {
            if(this==null){
                Toast.makeText(this@LoginActivity,getString(R.string.has_not_already),Toast.LENGTH_LONG).show()
                finish()
            }else{
                iConsoleService = this
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginProcessPrint = findViewById(R.id.loginProcessPrint)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        if(scanQrcodeLoginMode){
            findViewById<TextView>(R.id.protocolAdvice).visibility = View.INVISIBLE
            username.visibility = View.INVISIBLE
            password.visibility = View.INVISIBLE
            login.text = "点击扫码登录"
            login.isEnabled = true
            radioGroup.visibility = View.INVISIBLE
        }
        val loading = findViewById<ProgressBar>(R.id.loading)
        val protocolWatch = findViewById<RadioButton>(R.id.protocol_watch)
        val protocolPad = findViewById<RadioButton>(R.id.protocol_pad)
        val protocolIPad = findViewById<RadioButton>(R.id.protocol_ipad)
        val protocolMac = findViewById<RadioButton>(R.id.protocol_mac)
        val protocolAndroidPhone = findViewById<RadioButton>(R.id.protocol_android_phone)
        ////////////////////////////////////

        connectConsoleService()
        registerLogToiService()

        radioGroup.setOnCheckedChangeListener{ _: RadioGroup, _: Int ->
            when{
                protocolAndroidPhone.isChecked -> LoginProtocolType.ANDROID_PHONE
                protocolWatch.isChecked -> LoginProtocolType.ANDROID_WATCH
                protocolPad.isChecked -> LoginProtocolType.ANDROID_PAD
                protocolIPad.isChecked -> LoginProtocolType.IPAD
                protocolMac.isChecked -> LoginProtocolType.MAC
                else -> null
            }?.also {
                loginProtocol = it
            }
        }
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            loading.visibility = View.GONE
            loginProcessPrint.visibility = View.GONE
            unRegisterLogToiService()
            if(!scanQrcodeLoginMode) {
                login.isEnabled = loginState.isDataValid
                // disable login button unless both username / password is valid
                if (loginState.usernameError != null) {
                    username.error = getString(loginState.usernameError)
                }
                if (loginState.passwordError != null) {
                    if (loginState.serverResult == null) {
                        password.error = getString(loginState.passwordError)
                    } else {
                        password.error =
                            getString(loginState.passwordError) + ": \n" + loginState.serverResult
                    }
                    if (this::cancelThread.isInitialized) {
                        if (cancelThread.isAlive) {
                            cancelThread.interrupt()
                        }
                    }
                    cancelThread = Thread {
                        try {
                            Thread.sleep(5000)
                        } catch (e: Throwable) {

                        }
                        runOnUiThread {
                            password.error = null
                        }
                    }
                    cancelThread.start()
                }
            }else{
                login.isEnabled = true
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            loginProcessPrint.visibility = View.GONE
            loading.visibility = View.GONE
            unRegisterLogToiService()
            login.isEnabled = true
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            } else {
                updateUiWithUser()
            }
            setResult(Activity.RESULT_OK)
            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString(),
                                loginProtocol
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                connectConsoleService()
                registerLogToiService()
                loginProcessPrint.visibility = View.VISIBLE
                login.isEnabled=false
                loginViewModel.login(username.text.toString(), password.text.toString(), loginProtocol)
            }
        }
    }

    private fun updateUiWithUser() {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                welcome,
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
    private fun unRegisterLogToiService(){
        if(this::iConsoleService.isInitialized) {
            try {
                iConsoleService.unRegistLogCallback(TAG)
            }catch (e:DeadObjectException){
            }
        }
    }
    private fun registerLogToiService(){
        if(this::iConsoleService.isInitialized) {
            try{
                iConsoleService.registLogCallback(TAG,object : ILogCallback.Stub() {
                    val maxLogLine = 10
                    val logs=ArrayList<Spanned>()
                    init{
                        loginProcessPrint.visibility = View.GONE
                        loginProcessPrint.text = ""
                        repeat(maxLogLine){
                            logs.add(AndroidHtml.fromHtml(""))
                        }
                    }
                    override fun logChanged(text: String) {
                        Handler(Looper.getMainLooper()).post {
                            if(loginProcessPrint.visibility == View.VISIBLE) {
                                logs.add(AndroidHtml.fromHtml(text))
                                if(logs.size>=maxLogLine){
                                    logs.removeAt(0)
                                }
                                loginProcessPrint.text = ""
                                for(log in logs){
                                    loginProcessPrint.append(log)
                                    loginProcessPrint.append(AndroidHtml.fromHtml("<br/>"))
                                }
                            }
                        }
                    }
                })
            }catch (e: DeadObjectException){
            }
        }
    }
}

/**
 * Extension java.util.function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}