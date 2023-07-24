package ru.korobeynikov.p03additionalcapabilities.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p03additionalcapabilities.other.DatabaseHelper
import ru.korobeynikov.p03additionalcapabilities.other.MainActivityPresenter
import ru.korobeynikov.p03additionalcapabilities.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}