package ru.korobeynikov.p27jetpacknavigationbottomnavigationmenu.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavHostScreen() {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Screen1) }
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("Screen 1")
                    },
                    selected = backStack.last() == Screen.Screen1,
                    onClick = {
                        backStack.add(Screen.Screen1)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("Screen 2")
                    },
                    selected = backStack.last() == Screen.Screen2,
                    onClick = {
                        backStack.add(Screen.Screen2)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("Screen 3")
                    },
                    selected = backStack.last() == Screen.Screen3,
                    onClick = {
                        backStack.add(Screen.Screen3)
                    }
                )
            }
        }) { scaffoldPadding ->
            NavDisplay(
                modifier = Modifier.padding(scaffoldPadding),
                backStack = backStack,
                entryProvider = entryProvider {
                    entry<Screen.Screen1> { Screen1(backStack) }
                    entry<Screen.Screen2> { Screen2(backStack) }
                    entry<Screen.Screen3> { Screen3(backStack) }
                }
            )
        }
    }
}