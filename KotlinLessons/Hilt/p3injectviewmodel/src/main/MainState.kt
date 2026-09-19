package ru.korobeynikov.p3injectviewmodel.main

import ru.korobeynikov.p3injectviewmodel.Utils
import ru.korobeynikov.p3injectviewmodel.database.DatabaseHelper

data class MainState(
    val databaseHelper: DatabaseHelper,
    val utils: Utils
)
