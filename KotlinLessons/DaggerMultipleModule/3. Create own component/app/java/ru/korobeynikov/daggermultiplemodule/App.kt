package ru.korobeynikov.daggermultiplemodule

import android.app.Application

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = MyAppComponent()
    }
}