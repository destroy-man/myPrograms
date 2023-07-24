package ru.korobeynikov.p05objecttransmitmenttocomponent.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.korobeynikov.p05objecttransmitmenttocomponent.other.DatabaseHelper

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)
}