package ru.korobeynikov.p25jetpacknavigationdatatransfer.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavHostScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val backStack =
            remember { mutableStateListOf<Screen>(Screen.Screen1) }
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<Screen.Screen1> { Screen1(backStack) }
                entry<Screen.Screen2> { screen2 ->
                    Screen2(backStack, screen2.userText)
                }
                entry<Screen.Screen3> { screen3 ->
                    Screen3(backStack, screen3.user)
                }
            }
        )
    }
}