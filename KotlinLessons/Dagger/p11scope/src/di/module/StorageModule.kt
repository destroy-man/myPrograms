package ru.korobeynikov.p11scope.di.module

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.database.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}