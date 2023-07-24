package ru.korobeynikov.p04annotationinject.other

import android.app.Application
import ru.korobeynikov.p04annotationinject.di.AppComponent
import ru.korobeynikov.p04annotationinject.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}