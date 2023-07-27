package ru.korobeynikov.p09componentsdependencies.other

import android.app.Application
import ru.korobeynikov.p09componentsdependencies.di.AppComponent
import ru.korobeynikov.p09componentsdependencies.di.DaggerAppComponent
import ru.korobeynikov.p09componentsdependencies.di.DaggerMainComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        val mainComponent = DaggerMainComponent.builder().appComponent(appComponent).build()
    }
}