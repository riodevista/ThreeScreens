package ru.looktv.launcher.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.looktv.launcher.domain.UserRepository
import ru.looktv.launcher.utils.Event

class LogoutScreenViewModel() : ViewModel() {
    private val _nextScreenEvent = MutableStateFlow(Event(false))
    val nextScreenEvent: StateFlow<Event<Boolean>> get() = _nextScreenEvent
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    fun obtainUsername(context: Context) {
        _username.tryEmit(UserRepository.getUsername(context))
    }

    fun logout(context: Context) {
        UserRepository.clearUsername(context)
        _nextScreenEvent.tryEmit(Event(true))
    }
}
