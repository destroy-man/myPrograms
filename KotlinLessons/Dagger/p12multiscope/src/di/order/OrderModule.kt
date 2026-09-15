package ru.korobeynikov.p12multiscope.di.order

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.UiHelper

@Module
class OrderModule {
    @OrderScope
    @Provides
    fun provideUiHelper(activity: Activity): UiHelper {
        return UiHelper(activity)
    }
}