package ru.korobeynikov.data

import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideDatabase() = Database()
}