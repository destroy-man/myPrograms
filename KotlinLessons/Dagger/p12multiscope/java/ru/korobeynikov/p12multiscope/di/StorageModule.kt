package ru.korobeynikov.p12multiscope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}