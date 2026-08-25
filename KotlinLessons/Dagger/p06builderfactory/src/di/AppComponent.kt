package ru.korobeynikov.p06builderfactory.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.p06builderfactory.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class, AppModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    //Builder
//    @Component.Builder
//    interface AppCompBuilder {
//
//        fun buildAppComp(): AppComponent
//
//        //Модуль с параметром в основном конструкторе
//        //fun appModule(appModule: AppModule): AppCompBuilder
//
//        @BindsInstance
//        fun context(context: Context): AppCompBuilder
//    }

    @Component.Factory
    interface AppCompFactory {
        fun create(@BindsInstance context: Context, networkModule: NetworkModule): AppComponent
    }
}