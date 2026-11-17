package ru.korobeynikov.p26jetpacknavigationtransitionanimations.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
            transitionSpec = {
                ContentTransform(
                    fadeIn(animationSpec = tween(300))
                            + slideInHorizontally { it },
                    fadeOut(animationSpec = tween(300))
                            + slideOutHorizontally { -it }
                )
            },
            popTransitionSpec = {
                ContentTransform(
                    fadeIn(animationSpec = tween(300))
                            + slideInHorizontally { -it },
                    fadeOut(animationSpec = tween(300))
                            + slideOutHorizontally { it }
                )
            },
            entryProvider = entryProvider {
                entry<Screen.Screen1> { Screen1(backStack) }
                entry<Screen.Screen2> { screen2 ->
                    Screen2(backStack, screen2.user)
                }
                entry<Screen.Screen3> { Screen3(backStack) }
            }
        )
    }
}