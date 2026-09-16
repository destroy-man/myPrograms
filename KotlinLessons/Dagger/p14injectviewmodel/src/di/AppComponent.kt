package ru.korobeynikov.p14injectviewmodel.di

import dagger.Component
import ru.korobeynikov.p14injectviewmodel.MainActivity
import ru.korobeynikov.p14injectviewmodel.di.viewmodel.ViewModelBinderModule
import ru.korobeynikov.p14injectviewmodel.di.viewmodel.ViewModelProviderModule

@Component(
    modules = [
        StorageModule::class,
        NetworkModule::class,
        UtilsModule::class,
        ViewModelProviderModule::class,
        ViewModelBinderModule::class
    ]
)
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}