package ru.korobeynikov.p14fragmentviewmodel.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p14fragmentviewmodel.other.DatabaseHelper
import ru.korobeynikov.p14fragmentviewmodel.other.NetworkUtils
import ru.korobeynikov.p14fragmentviewmodel.other.ViewModelFactory

@Module
class MainModule {

    @Provides
    fun provideViewModelFactory(networkUtils: NetworkUtils, databaseHelper: DatabaseHelper) =
        ViewModelFactory(networkUtils, databaseHelper)

    @Provides
    fun provideDatabaseHelper() = DatabaseHelper()

    @Provides
    fun provideNetworkUtils() = NetworkUtils()
}