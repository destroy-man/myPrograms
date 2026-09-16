package ru.korobeynikov.p14injectviewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.p14injectviewmodel.viewmodels.OrderViewModel
import ru.korobeynikov.p14injectviewmodel.viewmodels.UserViewModel

@Composable
fun MainScreen(orderViewModel: OrderViewModel, userViewModel: UserViewModel) {
    Column {
        Text("order view model: $orderViewModel")
        Text("user view model: $userViewModel")
    }
}