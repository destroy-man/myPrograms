package ru.korobeynikov.p12multiscope

import android.app.Application
import ru.korobeynikov.p12multiscope.di.AppComponent
import ru.korobeynikov.p12multiscope.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}