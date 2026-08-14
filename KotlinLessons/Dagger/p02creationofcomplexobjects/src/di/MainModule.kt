package ru.korobeynikov.p02creationofcomplexobjects.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02creationofcomplexobjects.database.DatabaseHelper
import ru.korobeynikov.p02creationofcomplexobjects.main.MainActivityRepository
import ru.korobeynikov.p02creationofcomplexobjects.Utils

@Module
class MainModule {
    @Provides
    fun provideMainActivityRepository(
        databaseHelper: DatabaseHelper,
        networkUtils: Utils
    ): MainActivityRepository {
        return MainActivityRepository(databaseHelper, networkUtils)
    }
}