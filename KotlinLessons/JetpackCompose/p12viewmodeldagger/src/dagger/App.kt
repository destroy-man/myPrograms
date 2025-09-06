package ru.korobeynikov.p12viewmodel.dagger

import android.app.Application

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}