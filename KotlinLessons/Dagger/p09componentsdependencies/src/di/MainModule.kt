package ru.korobeynikov.p09componentsdependencies.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p09componentsdependencies.Utils
import ru.korobeynikov.p09componentsdependencies.database.DatabaseHelper
import ru.korobeynikov.p09componentsdependencies.main.MainActivityRepository

@Module
class MainModule {
    @Provides
    fun provideMainActivityRepository(
        databaseHelper: DatabaseHelper,
        utils: Utils
    ): MainActivityRepository {
        return MainActivityRepository(databaseHelper, utils)
    }
}