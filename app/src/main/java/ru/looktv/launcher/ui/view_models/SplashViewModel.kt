package ru.looktv.launcher.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looktv.launcher.utils.Event

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