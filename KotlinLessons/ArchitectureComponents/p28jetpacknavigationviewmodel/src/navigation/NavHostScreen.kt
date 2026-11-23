package ru.korobeynikov.p28jetpacknavigationviewmodel.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavHostScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val backStack = remember { mutableStateListOf<Screen>(Screen.Screen1) }
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                //Нужно чтобы для каждого экрана создавалась своя ViewModel
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Screen.Screen1> { Screen1(backStack) }
                entry<Screen.Screen2> { Screen2(backStack) }
                entry<Screen.Screen3> { Screen3(backStack) }
            }
        )
    }
}