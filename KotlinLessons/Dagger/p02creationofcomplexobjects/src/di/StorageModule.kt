package ru.korobeynikov.p02creationofcomplexobjects.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02creationofcomplexobjects.database.DatabaseHelper
import ru.korobeynikov.p02creationofcomplexobjects.database.DatabaseRepository

@Module
class StorageModule {

    @Provides
    fun provideDatabaseRepository(): DatabaseRepository {
        return DatabaseRepository()
    }

    @Provides
    fun provideDatabaseHelper(databaseRepository: DatabaseRepository): DatabaseHelper {
        return DatabaseHelper(databaseRepository)
    }
}