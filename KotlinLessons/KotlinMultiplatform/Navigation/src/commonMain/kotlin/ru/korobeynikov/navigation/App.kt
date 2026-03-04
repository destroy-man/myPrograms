package ru.korobeynikov.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Composable
@Preview
fun App() {
    Column(modifier = Modifier.safeContentPadding().fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.weight(1f)
        ) {
            composable("home") { HomeScreen() }
            composable("orders") { OrdersScreen() }
            composable<User> { backStackEntry ->
                val user = backStackEntry.toRoute<User>()
                UsersScreen(user.id)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Home", modifier = Modifier.clickable { navController.navigate("home") })
            Text(
                text = "Orders",
                modifier = Modifier.clickable { navController.navigate("orders") }
            )
            Text(
                text = "Users",
                modifier = Modifier.clickable {
                    navController.navigate(User("123"))
                }
            )
        }
    }
}

@Serializable
data class User(val id: String)