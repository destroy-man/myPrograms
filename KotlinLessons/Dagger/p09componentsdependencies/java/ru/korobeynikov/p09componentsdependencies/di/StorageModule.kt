package ru.korobeynikov.p09componentsdependencies.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p09componentsdependencies.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}