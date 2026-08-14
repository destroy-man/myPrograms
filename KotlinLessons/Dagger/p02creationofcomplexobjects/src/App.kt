package ru.korobeynikov.p02creationofcomplexobjects

import android.app.Application
import ru.korobeynikov.p02creationofcomplexobjects.di.AppComponent
import ru.korobeynikov.p02creationofcomplexobjects.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}