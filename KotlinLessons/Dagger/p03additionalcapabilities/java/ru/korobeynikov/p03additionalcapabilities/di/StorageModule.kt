package ru.korobeynikov.p03additionalcapabilities.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p03additionalcapabilities.other.DatabaseHelper
import ru.korobeynikov.p03additionalcapabilities.other.Repository

@Module
class StorageModule {

    @Provides
    fun provideDatabaseHelper(repository: Repository) = DatabaseHelper(repository)

    @Provides
    fun provideRepository() = Repository()
}