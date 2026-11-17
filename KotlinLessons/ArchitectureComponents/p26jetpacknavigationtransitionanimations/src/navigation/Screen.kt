package ru.korobeynikov.p26jetpacknavigationtransitionanimations.navigation

import ru.korobeynikov.p26jetpacknavigationtransitionanimations.User

sealed interface Screen {
    data object Screen1 : Screen
    data class Screen2(val user: User) : Screen
    data object Screen3 : Screen
}