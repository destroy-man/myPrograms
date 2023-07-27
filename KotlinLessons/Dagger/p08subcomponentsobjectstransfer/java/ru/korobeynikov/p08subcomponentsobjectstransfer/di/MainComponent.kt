package ru.korobeynikov.p08subcomponentsobjectstransfer.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.MainActivityPresenter

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): MainComponent
    }

    fun getMainActivityPresenter(): MainActivityPresenter
}