package ru.korobeynikov.p11scope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}