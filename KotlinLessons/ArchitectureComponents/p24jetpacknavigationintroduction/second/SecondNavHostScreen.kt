package ru.korobeynikov.p24jetpacknavigationintroduction.second

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

@Composable
fun SecondNavHostScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val backStack =
            remember { mutableStateListOf<SecondActivityScreen>(SecondActivityScreen.Screen4) }
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<SecondActivityScreen.Screen4> { Screen4(backStack) }
                entry<SecondActivityScreen.Screen5> { Screen5(backStack) }
            }
        )
    }
}