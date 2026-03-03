package ru.korobeynikov.localtimeapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform