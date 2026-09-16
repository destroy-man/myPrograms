package ru.korobeynikov.p13injectobjectswithprimitiveparameters

import android.app.Application
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.di.AppComponent
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}