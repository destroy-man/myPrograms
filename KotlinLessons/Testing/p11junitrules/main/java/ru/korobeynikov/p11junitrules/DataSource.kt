package ru.korobeynikov.p11junitrules

class DataSource(private val address: String) {

    fun connect() = log("connect $address")

    fun disconnect() = log("disconnect $address")

    private fun log(text: String) = println(text)
}