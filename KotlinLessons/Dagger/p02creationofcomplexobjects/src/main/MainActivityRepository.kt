package ru.korobeynikov.p02creationofcomplexobjects.main

import ru.korobeynikov.p02creationofcomplexobjects.database.DatabaseHelper
import ru.korobeynikov.p02creationofcomplexobjects.Utils

class MainActivityRepository(
    private val databaseHelper: DatabaseHelper,
    private val networkUtils: Utils
)