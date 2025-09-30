package ru.korobeynikov.p14navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(onNavigateToOrders: () -> Unit, onNavigateToUsers: () -> Unit) {
    Column {
        Text(text = "Home screen")
        Text(text = "Orders", modifier = Modifier.clickable(onClick = onNavigateToOrders))
        Text(text = "Users", modifier = Modifier.clickable(onClick = onNavigateToUsers))
    }
}

@Composable
fun OrdersScreen() {
    Text(text = "Orders screen")
}

@Composable
fun UsersScreen(navController: NavController, countUsers: Int) {
    val usersIdList = List(countUsers) { "${it + 1}" }
    LazyColumn {
        item {
            Text(text = "Users screen")
        }
        items(count = countUsers) { id ->
            Text(
                "user ${usersIdList[id]}",
                modifier = Modifier.clickable { navController.navigate("user/${id + 1}") }
            )
        }
    }
}

@Composable
fun UserScreen(id: String?) {
    Text(text = "User $id")
}