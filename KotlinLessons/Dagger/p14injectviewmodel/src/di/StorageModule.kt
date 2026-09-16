package ru.korobeynikov.p14injectviewmodel.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p14injectviewmodel.database.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}