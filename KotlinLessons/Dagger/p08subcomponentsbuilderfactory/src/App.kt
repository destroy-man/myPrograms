package ru.korobeynikov.p08subcomponentsbuilderfactory

import android.app.Application
import ru.korobeynikov.p08subcomponentsbuilderfactory.di.AppComponent
import ru.korobeynikov.p08subcomponentsbuilderfactory.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}