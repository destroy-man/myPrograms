package ru.korobeynikov.p03additionalfeatures

import android.app.Application
import ru.korobeynikov.p03additionalfeatures.di.AppComponent
import ru.korobeynikov.p03additionalfeatures.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}