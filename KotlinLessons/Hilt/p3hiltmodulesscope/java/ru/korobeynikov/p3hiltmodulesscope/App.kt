package ru.korobeynikov.p3hiltmodulesscope

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val tag = "myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "networkUtils $networkUtils")
    }
}