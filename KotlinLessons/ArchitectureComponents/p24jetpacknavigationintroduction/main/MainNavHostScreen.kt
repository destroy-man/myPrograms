package ru.korobeynikov.p24jetpacknavigationintroduction.main

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.korobeynikov.p24jetpacknavigationintroduction.second.SecondActivity

@Composable
fun MainNavHostScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val backStack =
            remember { mutableStateListOf<MainActivityScreen>(MainActivityScreen.Screen1) }
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<MainActivityScreen.Screen1> { Screen1(backStack) }
                entry<MainActivityScreen.Screen2> { Screen2(backStack) }
                entry<MainActivityScreen.Screen3> { Screen3(backStack) }
                entry<MainActivityScreen.SecondActivity> {
                    val context = LocalContext.current
                    val intent = Intent(context, SecondActivity::class.java)
                    context.startActivity(intent)
                }
            }
        )
    }
}