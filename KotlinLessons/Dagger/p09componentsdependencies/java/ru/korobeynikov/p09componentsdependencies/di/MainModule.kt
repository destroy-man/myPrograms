package ru.korobeynikov.p09componentsdependencies.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p09componentsdependencies.other.DatabaseHelper
import ru.korobeynikov.p09componentsdependencies.other.MainActivityPresenter
import ru.korobeynikov.p09componentsdependencies.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}