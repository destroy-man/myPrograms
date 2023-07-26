package ru.korobeynikov.p07subcomponents.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p07subcomponents.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}