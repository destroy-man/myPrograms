package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p08subcomponentsbuilderfactory.Utils
import ru.korobeynikov.p08subcomponentsbuilderfactory.database.DatabaseHelper
import ru.korobeynikov.p08subcomponentsbuilderfactory.main.MainActivityRepository

@Module
class MainModule {
    @Provides
    fun provideMainActivityRepository(
        databaseHelper: DatabaseHelper,
        utils: Utils,
        activity: Activity
    ): MainActivityRepository {
        return MainActivityRepository(databaseHelper, utils, activity)
    }
}