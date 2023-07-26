package ru.korobeynikov.p07subcomponents.other

import android.app.Application
import ru.korobeynikov.p07subcomponents.di.AppComponent
import ru.korobeynikov.p07subcomponents.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}