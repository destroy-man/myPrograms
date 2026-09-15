package ru.korobeynikov.p12multiscope.di.user

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.UiHelper

@Module
class UserModule {
    @UserScope
    @Provides
    fun provideUiHelper(activity: Activity): UiHelper {
        return UiHelper(activity)
    }
}