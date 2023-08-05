package ru.korobeynikov.p5hiltentrypoint.other

import javax.inject.Inject

class MyConnection @Inject constructor() : Connection {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onConnect() {}

    override fun onDisconnect() {}
}