package ru.korobeynikov.databasegames

data class Game(
    val id: Int,
    val name: String,
    val rating: Int,
    val year: Int,
    val genre: Int
)
