package ru.korobeynikov.chapter2

import kotlin.random.Random

class Rectangle(private val height: Int, private val width: Int) {
    val isSquare: Boolean
        get() = height == width
}

fun createRandomRectangle(): Rectangle {
    val random = Random
    return Rectangle(random.nextInt(), random.nextInt())
}