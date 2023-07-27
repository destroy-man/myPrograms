package ru.korobeynikov.p08subcomponentsobjectstransfer.other

import android.app.Application
import ru.korobeynikov.p08subcomponentsobjectstransfer.di.AppComponent
import ru.korobeynikov.p08subcomponentsobjectstransfer.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}