package ru.korobeynikov.p13assistedinject.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13assistedinject.other.UiHelper

@Module
class UserModule {
    @UserScope
    @Provides
    fun provideUiHelper(activity: Activity) = UiHelper(activity)
}