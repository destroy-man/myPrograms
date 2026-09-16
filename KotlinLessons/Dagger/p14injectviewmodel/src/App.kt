package ru.korobeynikov.p14injectviewmodel

import android.app.Application
import ru.korobeynikov.p14injectviewmodel.di.AppComponent
import ru.korobeynikov.p14injectviewmodel.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}