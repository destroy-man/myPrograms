package ru.korobeynikov.data

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideDatabase(context: Context) = Database(context)
}