package ru.korobeynikov.p02dependenciesgraph.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02dependenciesgraph.other.DatabaseHelper
import ru.korobeynikov.p02dependenciesgraph.other.MainActivityPresenter
import ru.korobeynikov.p02dependenciesgraph.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}