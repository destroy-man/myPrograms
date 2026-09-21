package ru.korobeynikov.p4entrypoint.connection

interface Connection {

    fun onConnect()

    fun onDisconnect()
}