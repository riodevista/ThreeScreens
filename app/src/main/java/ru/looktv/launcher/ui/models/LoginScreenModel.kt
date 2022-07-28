package ru.looktv.launcher.ui.models

import ru.looktv.launcher.utils.Event


data class LoginScreenModel(
    val showUsernameError: Boolean = false,
    val showPasswordError: Boolean = false,
    val openNextScreenEvent: Event<Boolean>? = null
)
