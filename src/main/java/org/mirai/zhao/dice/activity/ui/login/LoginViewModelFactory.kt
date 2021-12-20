package org.mirai.zhao.dice.activity.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.mirai.zhao.dice.activity.data.LoginRepository
import java.lang.ref.SoftReference

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(context: Activity) : ViewModelProvider.Factory {
    private val context: SoftReference<Activity> = SoftReference(context)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginRepository = LoginRepository(),
                    context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}