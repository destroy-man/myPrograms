package ru.korobeynikov.p13assistedinject.other

import android.app.Application
import ru.korobeynikov.p13assistedinject.di.AppComponent
import ru.korobeynikov.p13assistedinject.di.DaggerAppComponent
import ru.korobeynikov.p13assistedinject.di.DaggerMainComponent
import ru.korobeynikov.p13assistedinject.di.MainComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
    val mainComponent: MainComponent = DaggerMainComponent.create()
}