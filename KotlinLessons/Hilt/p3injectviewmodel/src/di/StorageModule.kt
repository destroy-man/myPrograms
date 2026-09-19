package ru.korobeynikov.p3injectviewmodel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.p3injectviewmodel.database.DatabaseHelper

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    fun providesDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}