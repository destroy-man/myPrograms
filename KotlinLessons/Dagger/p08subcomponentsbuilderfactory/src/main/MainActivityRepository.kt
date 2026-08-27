package ru.korobeynikov.p08subcomponentsbuilderfactory.main

import android.app.Activity
import ru.korobeynikov.p08subcomponentsbuilderfactory.Utils
import ru.korobeynikov.p08subcomponentsbuilderfactory.database.DatabaseHelper

class MainActivityRepository(
    private val databaseHelper: DatabaseHelper,
    private val utils: Utils,
    private val activity: Activity
)