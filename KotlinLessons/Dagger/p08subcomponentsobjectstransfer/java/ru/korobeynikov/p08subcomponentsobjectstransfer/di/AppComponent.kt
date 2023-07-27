package ru.korobeynikov.p08subcomponentsobjectstransfer.di

import dagger.Component
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.MainActivity

@Component(modules = [AppModule::class, StorageModule::class, NetworkModule::class])
interface AppComponent {
    fun injectMainActivity(activity: MainActivity)
}