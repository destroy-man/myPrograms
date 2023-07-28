package ru.korobeynikov.p10componentslivetime.other

import android.app.Application
import ru.korobeynikov.p10componentslivetime.di.AppComponent
import ru.korobeynikov.p10componentslivetime.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}