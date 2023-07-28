package ru.korobeynikov.p10componentslivetime.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p10componentslivetime.other.DatabaseHelper
import ru.korobeynikov.p10componentslivetime.other.MainActivityPresenter
import ru.korobeynikov.p10componentslivetime.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils) =
        MainActivityPresenter(databaseHelper, networkUtils)
}