package ru.korobeynikov.p05passingobjecttocomponent.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p05passingobjecttocomponent.database.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }
}