package ru.korobeynikov.daggermultiplemodule

import android.app.Application
import ru.korobeynikov.task.di.TaskComponentDependencies
import ru.korobeynikov.task.di.TaskComponentDependenciesProvider

class App : Application(), TaskComponentDependenciesProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        //appComponent = DaggerAppComponent.factory().create(this)
        appComponent = MyAppComponent(this)
    }

    override fun getTaskComponentDependencies(): TaskComponentDependencies = appComponent
}