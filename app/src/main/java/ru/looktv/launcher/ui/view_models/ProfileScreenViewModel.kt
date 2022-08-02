package ru.looktv.launcher.ui.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.looktv.launcher.ui.models.PaymentHistoryModel
import ru.looktv.launcher.ui.models.PaymentMethodModel
import ru.looktv.launcher.ui.models.ProfileScreenModel
import ru.looktv.launcher.ui.models.ProfileSubscriptionModel


class ProfileScreenViewModel() : ViewModel() {

    private val _screenModel = MutableStateFlow(
        ProfileScreenModel(
            subscription = ProfileSubscriptionModel("Стандарт +START", true),
            paymentMethods = listOf(PaymentMethodModel("•••1447", "МИР")),
            paymentsHistory = listOf(PaymentHistoryModel("Стандарт +START", "699 ₽"))
        )
    )
    val screenModel: StateFlow<ProfileScreenModel> get() = _screenModel

}
