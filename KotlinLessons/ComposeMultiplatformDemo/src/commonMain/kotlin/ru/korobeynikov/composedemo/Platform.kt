package ru.korobeynikov.composedemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform