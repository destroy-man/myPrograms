package ru.korobeynikov.p05objecttransmitmenttocomponent.other

import android.app.Application
import ru.korobeynikov.p05objecttransmitmenttocomponent.di.AppComponent
import ru.korobeynikov.p05objecttransmitmenttocomponent.di.AppModule
import ru.korobeynikov.p05objecttransmitmenttocomponent.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}