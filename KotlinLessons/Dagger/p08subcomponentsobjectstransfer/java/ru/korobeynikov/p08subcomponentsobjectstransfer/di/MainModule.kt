package ru.korobeynikov.p08subcomponentsobjectstransfer.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.DatabaseHelper
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.MainActivityPresenter
import ru.korobeynikov.p08subcomponentsobjectstransfer.other.NetworkUtils

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper, networkUtils: NetworkUtils,
        activity: Activity) = MainActivityPresenter(databaseHelper, networkUtils, activity)
}