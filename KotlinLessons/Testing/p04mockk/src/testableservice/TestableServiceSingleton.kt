package ru.korobeynikov.p04mockk.testableservice

object TestableServiceSingleton {
    fun getDataFromDb(testParameter: String): String {
        return "data"
    }
}