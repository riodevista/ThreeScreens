package com.goodapps.threescreens.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.goodapps.threescreens.domain.UserRepository
import com.goodapps.threescreens.ui.models.LoginScreenModel
import com.goodapps.threescreens.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel() : ViewModel() {
    private val _screenModel = MutableStateFlow(LoginScreenModel())
    val screenModel: StateFlow<LoginScreenModel> get() = _screenModel

    fun login(context: Context, username: String, password: String) {
        val inputCorrect = verifyFields(username, password)
        if (inputCorrect) {
            UserRepository.saveUsername(context = context, username = username)
            _screenModel.update { it.copy(openNextScreenEvent = Event(true)) }
        }
    }

    fun verifyFields(username: String? = null, password: String? = null): Boolean {
        val usernameValid = username?.isNotBlank() ?: !_screenModel.value.showUsernameError
        val passwordValid = password?.isNotBlank() ?: !_screenModel.value.showPasswordError
        _screenModel.update {
            it.copy(
                showUsernameError = !usernameValid,
                showPasswordError = !passwordValid
            )
        }
        return usernameValid && passwordValid
    }
}
