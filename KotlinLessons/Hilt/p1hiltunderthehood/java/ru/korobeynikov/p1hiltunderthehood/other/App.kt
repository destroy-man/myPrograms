package ru.korobeynikov.p1hiltunderthehood.other

import android.util.Log
import javax.inject.Inject

class App : HiltApp() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()
        Log.d(MainActivity.TAG, "databaseHelper = $databaseHelper")
    }
}