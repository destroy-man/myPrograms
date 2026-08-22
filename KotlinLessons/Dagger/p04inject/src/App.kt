package ru.korobeynikov.p04inject

import android.app.Application
import ru.korobeynikov.p04inject.di.AppComponent
import ru.korobeynikov.p04inject.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}