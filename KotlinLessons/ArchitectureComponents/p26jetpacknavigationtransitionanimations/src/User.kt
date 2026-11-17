package ru.korobeynikov.p26jetpacknavigationtransitionanimations

import kotlinx.serialization.Serializable

@Serializable //Типобезопасный объект
data class User(
    val name: String,
    val surname: String
)
