package ru.korobeynikov.p12multiscope.di.user

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p12multiscope.UiHelper
import ru.korobeynikov.p12multiscope.di.ActivityScope

@ActivityScope
@UserScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    @Subcomponent.Factory
    interface UserFactory {
        fun create(@BindsInstance activity: Activity): UserComponent
    }

    fun getUiHelper(): UiHelper
}