package ru.korobeynikov.navigation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform