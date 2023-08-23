package ru.korobeynikov.p04mockobjects.repository

data class Order(
    private val id: Long,
    private val amount: Float,
    private val description: String
)
