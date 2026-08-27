package ru.korobeynikov.p09componentsdependencies

import android.app.Application
import ru.korobeynikov.p09componentsdependencies.di.AppComponent
import ru.korobeynikov.p09componentsdependencies.di.DaggerAppComponent
import ru.korobeynikov.p09componentsdependencies.di.DaggerMainComponent
import ru.korobeynikov.p09componentsdependencies.di.MainComponent

class App : Application() {

    lateinit var appComponent: AppComponent
    lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        mainComponent = DaggerMainComponent.builder().appComponent(appComponent).build()
    }
}