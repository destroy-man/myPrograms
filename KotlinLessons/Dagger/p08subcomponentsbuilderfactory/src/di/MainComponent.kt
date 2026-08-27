package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p08subcomponentsbuilderfactory.main.MainActivityRepository

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    //Builder
    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): MainComponent
    }

    //Factory
//    @Subcomponent.Factory
//    interface Factory {
//        fun create(@BindsInstance activity: Activity): MainComponent
//    }

    fun getMainActivityRepository(): MainActivityRepository
}