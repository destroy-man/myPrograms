package ru.korobeynikov.p06builderfactory.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.p06builderfactory.other.MainActivity

@Component(modules = [AppModule::class, StorageModule::class, NetworkModule::class, MainModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    @Component.Factory
    interface AppCompFactory {
        fun create(@BindsInstance context: Context, networkModule: NetworkModule): AppComponent
    }
}