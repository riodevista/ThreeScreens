package com.goodapps.threescreens.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.goodapps.threescreens.domain.UserRepository
import com.goodapps.threescreens.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel() : ViewModel() {
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
