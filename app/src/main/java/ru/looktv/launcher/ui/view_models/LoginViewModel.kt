package ru.looktv.launcher.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.looktv.launcher.domain.UserRepository
import ru.looktv.launcher.ui.models.LoginScreenModel
import ru.looktv.launcher.utils.Event

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
