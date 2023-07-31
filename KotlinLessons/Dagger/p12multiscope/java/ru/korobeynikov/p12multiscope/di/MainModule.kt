package ru.korobeynikov.p12multiscope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.other.DatabaseHelper
import ru.korobeynikov.p12multiscope.other.MainActivityPresenter
import ru.korobeynikov.p12multiscope.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}