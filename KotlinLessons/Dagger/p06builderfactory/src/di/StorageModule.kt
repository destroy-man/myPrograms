package ru.korobeynikov.p06builderfactory.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p06builderfactory.database.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }
}