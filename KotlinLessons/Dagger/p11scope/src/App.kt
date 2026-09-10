package ru.korobeynikov.p11scope

import android.app.Application
import ru.korobeynikov.p11scope.di.component.AppComponent
import ru.korobeynikov.p11scope.di.component.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}