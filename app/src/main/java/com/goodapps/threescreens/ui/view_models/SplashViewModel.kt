package com.goodapps.threescreens.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodapps.threescreens.utils.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel() : ViewModel() {
    private val _nextScreenEvent = MutableStateFlow(Event(false))
    val nextScreenEvent: StateFlow<Event<Boolean>> get() = _nextScreenEvent

    init {
        viewModelScope.launch {
            delay(3000)
            _nextScreenEvent.emit(Event(true))
        }
    }
}