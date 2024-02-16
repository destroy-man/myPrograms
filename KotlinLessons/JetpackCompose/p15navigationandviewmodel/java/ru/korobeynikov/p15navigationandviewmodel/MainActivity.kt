package ru.korobeynikov.p15navigationandviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                val activity = checkNotNull(LocalViewModelStoreOwner.current)
                NavHost(
                    navController = navController,
                    startDestination = "userList",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("userList") {
                        UserListScreen(onUserClick = {
                            navController.navigate("user")
                        }, usersSharedViewModel = viewModel(activity))
                    }
                    composable("user") {
                        UserScreen(usersSharedViewModel = viewModel(activity))
                    }
                }
                Text(
                    text = "Users",
                    modifier = Modifier.clickable { navController.navigate("userList") })
            }
        }
    }
}