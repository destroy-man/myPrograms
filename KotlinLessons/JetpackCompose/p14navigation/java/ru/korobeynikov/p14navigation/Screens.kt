package ru.korobeynikov.p14navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onNavigateToOrders: () -> Unit, onNavigateToUsers: () -> Unit) = Column {
    Text(text = "Home screen")
    Text(text = "Orders", modifier = Modifier.clickable(onClick = onNavigateToOrders))
    Text(text = "Users", modifier = Modifier.clickable(onClick = onNavigateToUsers))
}

@Composable
fun OrdersScreen() = Text(text = "Orders screen")

@Composable
fun UsersScreen(id: String?) = Text(text = "User $id")