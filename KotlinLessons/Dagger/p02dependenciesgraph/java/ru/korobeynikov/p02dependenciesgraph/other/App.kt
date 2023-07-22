package ru.korobeynikov.p02dependenciesgraph.other

import android.app.Application
import ru.korobeynikov.p02dependenciesgraph.di.AppComponent
import ru.korobeynikov.p02dependenciesgraph.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}