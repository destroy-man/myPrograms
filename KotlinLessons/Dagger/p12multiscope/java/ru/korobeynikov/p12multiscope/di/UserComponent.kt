package ru.korobeynikov.p12multiscope.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p12multiscope.other.UiHelper

@ActivityScope
@UserScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): UserComponent
    }

    fun getUiHelper(): UiHelper
}