package ru.korobeynikov.p25jetpacknavigationdatatransfer.navigation

import ru.korobeynikov.p25jetpacknavigationdatatransfer.User

sealed interface Screen {
    data object Screen1 : Screen
    data class Screen2(val userText: String) : Screen
    data class Screen3(val user: User) : Screen
}