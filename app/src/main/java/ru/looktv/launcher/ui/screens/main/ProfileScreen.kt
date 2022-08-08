package ru.looktv.launcher.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.CircleButton
import ru.looktv.launcher.ui.models.PaymentHistoryModel
import ru.looktv.launcher.ui.models.PaymentMethodModel
import ru.looktv.launcher.ui.models.ProfileSubscriptionModel
import ru.looktv.launcher.ui.view_models.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    back: () -> Unit
) {
    val viewModel: ProfileScreenViewModel = viewModel()
    val screenModel = viewModel.screenModel.collectAsState()

    val selectedTabItem = remember { mutableStateOf(0) }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomEnd)
                .alpha(0.5f),
            painter = painterResource(id = R.drawable.profile_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(top = 26.5.dp, start = 12.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CircleButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        iconId = R.drawable.ic_back,
                    ) {
                        back()
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .padding(bottom = 2.dp),
                        text = stringResource(R.string.profile),
                        color = colorResource(id = R.color.white),
                        fontSize = 32.sp
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 2.dp),
                    text = "Привет, пользователь!",
                    color = colorResource(id = R.color.white),
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(13.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth(0.72f)) {
                item { Spacer(modifier = Modifier.height(13.dp)) }
                item { SubscriptionItem(screenModel.value.subscription) }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item { PaymentMethodsItem(screenModel.value.paymentMethods) }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item { PaymentsHistoryItem(screenModel.value.paymentsHistory) }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

@Composable
fun SubscriptionItem(subscription: ProfileSubscriptionModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.white), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = subscription.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.darkText)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = if (subscription.active) "Активен" else "Неактивен",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF0EE38C)
                    )
                }
                Image(
                    modifier = Modifier
                        .size(width = 91.dp, height = 32.5.dp)
                        .focusable()
                        .clickable {},
                    painter = painterResource(id = R.drawable.ic_change_button),
                    contentDescription = "to_change",
                    contentScale = ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 9.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "699 ₽ / Мес.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.darkText)
                )
                Text(
                    modifier = Modifier
                        .alpha(0.4f)
                        .padding(bottom = 3.dp),
                    text = "Следующие \nспишутся 1 сентября",
                    fontSize = 9.sp,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.darkText)
                )
            }
        }
    }
}

@Composable
fun PaymentMethodsItem(paymentMethods: List<PaymentMethodModel>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(
            modifier = Modifier
                .size(184.5.dp, 117.5.dp)
                .clickable { },
            painter = painterResource(id = R.drawable.ic_card),
            contentDescription = "payment_method",
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .size(184.5.dp, 117.5.dp)
                .clickable { },
            painter = painterResource(id = R.drawable.ic_card_add),
            contentDescription = "payment_method",
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun PaymentsHistoryItem(paymentsHistory: List<PaymentHistoryModel>) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 6.7.dp),
            text = "История платежей",
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            color = colorResource(id = R.color.white)
        )
        Text(
            modifier = Modifier
                .padding(bottom = 7.3.dp)
                .alpha(0.3f),
            text = "31 июля 2022",
            fontSize = 8.sp,
            fontWeight = FontWeight(600),
            color = colorResource(id = R.color.white)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(5.3.dp)
                )
                .clip(RoundedCornerShape(5.3.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.5.dp, vertical = 21.5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = paymentsHistory[0].name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.darkText)
                )
                Text(
                    text = paymentsHistory[0].price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.darkText)
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 26.dp)
                .clickable { },
            text = stringResource(R.string.show_all_history),
            color = Color(0xFF8073FF),
            fontWeight = FontWeight(700),
            fontSize = 12.sp
        )
    }
}
