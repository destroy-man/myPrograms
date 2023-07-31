package ru.korobeynikov.p13assistedinject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13assistedinject.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}