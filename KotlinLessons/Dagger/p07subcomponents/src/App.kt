package ru.korobeynikov.p07subcomponents

import android.app.Application
import ru.korobeynikov.p07subcomponents.di.AppComponent
import ru.korobeynikov.p07subcomponents.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}