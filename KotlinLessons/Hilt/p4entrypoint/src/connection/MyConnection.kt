package ru.korobeynikov.p4entrypoint.connection

import ru.korobeynikov.p4entrypoint.database.DatabaseHelper
import javax.inject.Inject

class MyConnection : Connection {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onConnect() {}

    override fun onDisconnect() {}
}