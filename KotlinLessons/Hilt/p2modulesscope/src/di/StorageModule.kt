package ru.korobeynikov.p2modulesscope.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.p2modulesscope.database.DatabaseHelper

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    fun providesDatabaseHelper(app: Application): DatabaseHelper {
        return DatabaseHelper(app.applicationContext)
    }
}