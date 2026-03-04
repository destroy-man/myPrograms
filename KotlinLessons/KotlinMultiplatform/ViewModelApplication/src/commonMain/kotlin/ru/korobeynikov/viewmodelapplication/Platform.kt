package ru.korobeynikov.viewmodelapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform