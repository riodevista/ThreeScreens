package com.goodapps.threescreens.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goodapps.threescreens.R
import com.goodapps.threescreens.core.ui.common.Input
import com.goodapps.threescreens.core.ui.common.dpadFocusable
import com.goodapps.threescreens.core.ui.theme.Shapes
import com.goodapps.threescreens.ui.view_models.LoginViewModel

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    openMainScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel()
    val screenModel = viewModel.screenModel.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> {
                    keyboardController?.hide()

                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val nameInputFieldState = remember { mutableStateOf("") }
    val passwordInputFieldState = remember { mutableStateOf("") }
    val nameInputFocusRequester = FocusRequester()
    val passwordInputFocusRequester = FocusRequester()
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        UsernameInput(
            viewModel = viewModel,
            inputStateField = nameInputFieldState,
            showError = screenModel.value.showUsernameError,
            focusRequester = nameInputFocusRequester,
            keyboardController = keyboardController
        )
        Spacer(Modifier.height(16.dp))
        PasswordInput(
            viewModel = viewModel,
            inputStateField = passwordInputFieldState,
            showError = screenModel.value.showPasswordError,
            focusRequester = passwordInputFocusRequester
        )
        Spacer(Modifier.weight(0.5f))
        val loginButtonAction = {
            viewModel.login(
                context.applicationContext,
                nameInputFieldState.value,
                passwordInputFieldState.value
            )
        }
        LoginButton(clickAction = loginButtonAction)
        Spacer(Modifier.weight(3f))
    }

    screenModel.value.openNextScreenEvent?.let { event ->
        event.getContentIfNotHandled()?.let {
            if (it)
                openMainScreen()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun UsernameInput(
    viewModel: LoginViewModel,
    inputStateField: MutableState<String>,
    showError: Boolean,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?
) {
    Column {
        Input(
            modifier = Modifier.dpadFocusable(onClick = {
                focusRequester.requestFocus()
            }),
            text = inputStateField.value,
            onTextChange = {
                inputStateField.value = it
                viewModel.verifyFields(username = inputStateField.value)
            },
            hint = stringResource(R.string.username),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            focusRequester = focusRequester
        )
    }
    Box(Modifier.defaultMinSize(minHeight = 30.dp)) {
        if (showError) {
            Text(
                modifier = Modifier.padding(all = 4.dp),
                text = stringResource(R.string.enter_username),
                color = MaterialTheme.colors.error
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun PasswordInput(
    viewModel: LoginViewModel,
    inputStateField: MutableState<String>,
    showError: Boolean,
    focusRequester: FocusRequester
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column {
        Input(
            modifier = Modifier
                .dpadFocusable(onClick = {
                    focusRequester.requestFocus()
                }),
            text = inputStateField.value,
            onTextChange = {
                inputStateField.value = it
                viewModel.verifyFields(password = inputStateField.value)
            },
            hint = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            focusRequester = focusRequester,
            visualTransformation = PasswordVisualTransformation()
        )
        Box(Modifier.defaultMinSize(minHeight = 30.dp)) {
            if (showError) {
                Text(
                    modifier = Modifier.padding(all = 4.dp),
                    text = stringResource(R.string.enter_password),
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginButton(
    clickAction: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .dpadFocusable(clickAction)
            .height(52.dp),
        shape = Shapes.large,
        onClick = clickAction
    ) {
        Text(text = stringResource(R.string.login))
    }
}
