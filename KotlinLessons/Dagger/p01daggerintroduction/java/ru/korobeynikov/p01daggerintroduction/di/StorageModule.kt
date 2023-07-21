package ru.korobeynikov.p01daggerintroduction.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p01daggerintroduction.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}