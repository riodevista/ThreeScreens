package ru.looktv.launcher.ui.models


data class ProfileScreenModel(
    val subscription: ProfileSubscriptionModel,
    val paymentMethods: List<PaymentMethodModel>,
    val paymentsHistory: List<PaymentHistoryModel>
)

data class ProfileSubscriptionModel(
    val name: String,
    val active: Boolean,
)

data class PaymentMethodModel(
    val number: String,
    val type: String
)

data class PaymentHistoryModel(
    val name: String,
    val price: String
)
