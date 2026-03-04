package ru.korobeynikov.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Text(text = "Home screen")
}

@Composable
fun OrdersScreen() {
    Text(text = "Orders screen")
}

@Composable
fun UsersScreen(id: String?) {
    Text(text = "User $id")
}