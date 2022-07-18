package com.goodapps.threescreens.ui.models

import com.goodapps.threescreens.utils.Event


data class LoginScreenModel(
    val showUsernameError: Boolean = false,
    val showPasswordError: Boolean = false,
    val openNextScreenEvent: Event<Boolean>? = null
)
