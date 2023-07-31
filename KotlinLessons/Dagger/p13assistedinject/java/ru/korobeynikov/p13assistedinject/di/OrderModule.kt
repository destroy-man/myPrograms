package ru.korobeynikov.p13assistedinject.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13assistedinject.other.UiHelper

@Module
class OrderModule {
    @OrderScope
    @Provides
    fun provideUiHelper(activity: Activity) = UiHelper(activity)
}