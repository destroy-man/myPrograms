package ru.korobeynikov.p11scope.other

import android.app.Application
import ru.korobeynikov.p11scope.di.AppComponent
import ru.korobeynikov.p11scope.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}