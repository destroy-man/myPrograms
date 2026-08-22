package ru.korobeynikov.p05passingobjecttocomponent

import android.app.Application
import ru.korobeynikov.p05passingobjecttocomponent.di.AppComponent
import ru.korobeynikov.p05passingobjecttocomponent.di.AppModule
import ru.korobeynikov.p05passingobjecttocomponent.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}