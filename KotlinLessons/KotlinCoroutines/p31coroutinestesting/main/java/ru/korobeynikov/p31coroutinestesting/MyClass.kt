package ru.korobeynikov.p31coroutinestesting

import kotlinx.coroutines.delay

class MyClass {
    suspend fun someMethod(): String {
        delay(5000)
        return "abc"
    }
}