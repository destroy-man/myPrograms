package ru.korobeynikov.p04inject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p04inject.database.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}