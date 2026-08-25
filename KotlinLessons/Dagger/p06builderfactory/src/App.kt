package ru.korobeynikov.p06builderfactory

import android.app.Application
import ru.korobeynikov.p06builderfactory.di.AppComponent
import ru.korobeynikov.p06builderfactory.di.AppModule
import ru.korobeynikov.p06builderfactory.di.DaggerAppComponent
import ru.korobeynikov.p06builderfactory.di.NetworkModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        //Builder
//        appComponent = DaggerAppComponent
//            .builder()
//            //.appModule(AppModule(this)) Модуль с параметром в основном конструкторе
//            .context(this)
//            .buildAppComp()

        appComponent = DaggerAppComponent.factory().create(this, NetworkModule())
    }
}