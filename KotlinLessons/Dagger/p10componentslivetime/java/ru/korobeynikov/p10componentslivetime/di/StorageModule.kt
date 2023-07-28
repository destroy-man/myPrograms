package ru.korobeynikov.p10componentslivetime.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p10componentslivetime.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}