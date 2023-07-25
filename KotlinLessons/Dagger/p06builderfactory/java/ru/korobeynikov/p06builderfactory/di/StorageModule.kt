package ru.korobeynikov.p06builderfactory.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p06builderfactory.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}