package ru.korobeynikov.p2modulesscope.di

import android.app.Activity
import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.korobeynikov.p2modulesscope.network.NetworkUtils

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {
    @Provides
    fun provideNetworkUtils(app: Application, activity: Activity): NetworkUtils {
        return NetworkUtils(app, activity)
    }
}