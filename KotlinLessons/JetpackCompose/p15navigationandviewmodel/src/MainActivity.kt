package ru.korobeynikov.p15navigationandviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("User")
    lateinit var savedState: SavedStateHandle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                val activity = checkNotNull(LocalViewModelStoreOwner.current)
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "users",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("users") {
                        UsersScreen(
                            navController,
                            100
                        )
                    }
                    composable(
                        "user/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        // different ViewModel for every user
                        val userId = backStackEntry.arguments?.getString("id")
                        savedState["userId"] = userId
                        UserScreen(id = userId)

                        /* single ViewModel for every user, use NavBackStackEntry
                        val users = remember(backStackEntry) {
                            navController.getBackStackEntry("users")
                        }
                        val userId = backStackEntry.arguments?.getString("id")
                        UserScreen(id = userId, userViewModel = viewModel(users))
                         */

                        /* single ViewModel for every user, use Activity
                        val userId = backStackEntry.arguments?.getString("id")
                        UserScreen(id = userId, userViewModel = viewModel(activity))
                         */
                    }
                }
                Text(
                    text = "Users",
                    modifier = Modifier.clickable { navController.navigate("users") }
                )
            }
            /* Custom navigation
            Column(modifier = Modifier.fillMaxSize()) {
                var route by remember { mutableStateOf("users") }

                Box(modifier = Modifier.weight(1f)) {
                    when (route) {
                        "users" -> UsersScreenCustomNavigation(
                            onUser1Click = { route = "user/1" },
                            onUser2Click = { route = "user/2" }
                        )

                        "user/1" -> UserScreen("1")
                        "user/2" -> UserScreen("2")
                    }
                }

                Text(text = "Users", modifier = Modifier.clickable { route = "users" })
            }
            */
        }
    }
}