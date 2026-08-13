package ru.korobeynikov.p01introduction.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p01introduction.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}