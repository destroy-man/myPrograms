package ru.korobeynikov.p1hiltunderthehood.other

import android.app.Application
import ru.korobeynikov.p1hiltunderthehood.di.DaggerHiltAppComponent
import ru.korobeynikov.p1hiltunderthehood.di.HiltAppComponent

open class HiltApp : Application() {

    val appComponent: HiltAppComponent = DaggerHiltAppComponent.create()

    override fun onCreate() {
        super.onCreate()
        appComponent.injectApp(this as App)
    }
}