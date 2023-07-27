package ru.korobeynikov.p08subcomponentsobjectstransfer.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()
}