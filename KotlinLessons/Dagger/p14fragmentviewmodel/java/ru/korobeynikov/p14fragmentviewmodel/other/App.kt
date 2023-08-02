package ru.korobeynikov.p14fragmentviewmodel.other

import android.app.Application
import ru.korobeynikov.p14fragmentviewmodel.di.AppComponent
import ru.korobeynikov.p14fragmentviewmodel.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}