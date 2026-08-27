package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import dagger.Component
import ru.korobeynikov.p08subcomponentsbuilderfactory.main.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class])
interface AppComponent {

    //Передача параметра в метод
    //fun getMainComponent(mainModule: MainModule): MainComponent

    //Builder get метод
    //fun getMainComponentBuilder(): MainComponent.Builder

    //Factory get метод
    //fun getMainComponentFactory(): MainComponent.Factory

    fun injectMainActivity(activity: MainActivity)
}