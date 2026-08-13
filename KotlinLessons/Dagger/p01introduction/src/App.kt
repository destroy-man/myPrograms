package ru.korobeynikov.p01introduction

import android.app.Application
import ru.korobeynikov.p01introduction.di.AppComponent
import ru.korobeynikov.p01introduction.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}