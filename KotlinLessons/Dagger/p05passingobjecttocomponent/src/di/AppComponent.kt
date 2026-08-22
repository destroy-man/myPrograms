package ru.korobeynikov.p05passingobjecttocomponent.di

import dagger.Component
import ru.korobeynikov.p05passingobjecttocomponent.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class, AppModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}