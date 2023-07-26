package ru.korobeynikov.p07subcomponents.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p07subcomponents.other.DatabaseHelper
import ru.korobeynikov.p07subcomponents.other.MainActivityPresenter
import ru.korobeynikov.p07subcomponents.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}