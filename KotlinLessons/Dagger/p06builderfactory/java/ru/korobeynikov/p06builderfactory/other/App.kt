package ru.korobeynikov.p06builderfactory.other

import android.app.Application
import ru.korobeynikov.p06builderfactory.di.AppComponent
import ru.korobeynikov.p06builderfactory.di.DaggerAppComponent
import ru.korobeynikov.p06builderfactory.di.NetworkModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this, NetworkModule())
    }
}