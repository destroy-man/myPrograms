package ru.korobeynikov.p13injectobjectswithprimitiveparameters.di

import dagger.Component
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.main.MainActivity
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.network.ServerApi

@Component(modules = [NetworkModule::class, UtilsModule::class])
interface AppComponent {

    @AssistedFactory
    interface ServerApiFactory {
        fun create(
            @Assisted("host") host: String,
            @Assisted("port") port: String = "80"
        ): ServerApi
    }

    fun injectMainActivity(mainActivity: MainActivity)
}