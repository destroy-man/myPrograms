package ru.korobeynikov.p12multiscope.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.other.UiHelper

@Module
class OrderModule {
    @OrderScope
    @Provides
    fun provideUiHelper(activity: Activity) = UiHelper(activity)
}