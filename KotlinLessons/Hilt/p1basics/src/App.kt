package ru.korobeynikov.p1basics

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()
        Log.d("myLogs", "databaseHelper = $databaseHelper")
    }
}