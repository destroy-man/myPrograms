package ru.korobeynikov.daggermultiplemodule

import android.app.Application
import ru.korobeynikov.task.TaskComponent
import ru.korobeynikov.task.TaskComponentProvider

class App : Application(), TaskComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        //appComponent = DaggerAppComponent.factory().create(this)
        appComponent = MyAppComponent(this)
    }

    override fun getTaskComponent(): TaskComponent = appComponent.createTaskComponent()
}