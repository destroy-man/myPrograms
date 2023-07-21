package ru.korobeynikov.p01daggerintroduction.other

import android.app.Application
import ru.korobeynikov.p01daggerintroduction.di.AppComponent
import ru.korobeynikov.p01daggerintroduction.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}