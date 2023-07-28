package ru.korobeynikov.p11scope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.other.DatabaseHelper
import ru.korobeynikov.p11scope.other.MainActivityPresenter
import ru.korobeynikov.p11scope.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}