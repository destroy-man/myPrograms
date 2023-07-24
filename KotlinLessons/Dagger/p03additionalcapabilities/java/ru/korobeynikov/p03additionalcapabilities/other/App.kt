package ru.korobeynikov.p03additionalcapabilities.other

import android.app.Application
import ru.korobeynikov.p03additionalcapabilities.di.AppComponent
import ru.korobeynikov.p03additionalcapabilities.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}