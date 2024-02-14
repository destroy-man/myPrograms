package ru.korobeynikov.p12viewmodel

import android.app.Application
import ru.korobeynikov.p12viewmodel.di.AppComponent
import ru.korobeynikov.p12viewmodel.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}