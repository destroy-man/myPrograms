package ru.korobeynikov.p07subcomponents.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p07subcomponents.Utils
import ru.korobeynikov.p07subcomponents.database.DatabaseHelper
import ru.korobeynikov.p07subcomponents.main.MainActivityRepository

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