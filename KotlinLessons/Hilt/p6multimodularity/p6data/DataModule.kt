package ru.korobeynikov.p6data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DataModule {
    @Provides
    fun provideDatabase(): Database {
        return Database()
    }
}