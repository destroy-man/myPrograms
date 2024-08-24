package ru.korobeynikov.chapter11

class Greeter(private val greeting: String) {
    operator fun invoke(name: String) = " $greeting, $name!"
}