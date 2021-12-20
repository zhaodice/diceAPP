package org.mirai.zhao.dice.activity.ui.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.activity.data.LoginRepository
import org.mirai.zhao.dice.R
import org.mirai.zhao.dice.data.LoginProtocolType
import java.lang.ref.SoftReference
import java.util.regex.Pattern

class LoginViewModel(private val loginRepository: LoginRepository,private val context: SoftReference<Activity>) : ViewModel() {
    private val qqNumberPatterns: Pattern =Pattern.compile("[1-9][0-9]{4,14}")
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String,setProtocol: LoginProtocolType) {
        // can be launched in a separate asynchronous job
        val activity = context.get()
        if(activity==null){
            _loginForm.value = LoginFormState(passwordError = R.string.unknownError)
            return
        }
        if(password.length>16){
            _loginForm.value = LoginFormState(passwordError = R.string.password_over_16)
            return
        }
        val app=AppContext.context
        if(app==null || app.connectConsoleService().iConsoleService ==null) {
            _loginForm.value = LoginFormState(passwordError = R.string.has_not_already)
        }else {
            object : Thread() {
                override fun run() {
                    val result = loginRepository.login(username, password,setProtocol)
                    activity.runOnUiThread {
                        if (result.isEmpty()) {
                            _loginResult.value = LoginResult()
                        } else {
                            //_loginResult.value = LoginResult(error = R.string.login_failed)
                            _loginForm.value = LoginFormState(passwordError = R.string.login_failed,serverResult = result)
                        }
                    }
                    super.run()
                }
            }.start()
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
                _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            } else if (!isPasswordValid(password)) {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            } else {
                _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

        // A placeholder username validation check
        private fun isUserNameValid(username: String): Boolean {
            return qqNumberPatterns.matcher(username).matches()
        }

        // A placeholder password validation check
        private fun isPasswordValid(password: String): Boolean {
            return password.length > 5
    }
}