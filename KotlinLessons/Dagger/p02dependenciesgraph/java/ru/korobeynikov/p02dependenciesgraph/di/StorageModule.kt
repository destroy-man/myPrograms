package ru.korobeynikov.p02dependenciesgraph.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02dependenciesgraph.other.DatabaseHelper
import ru.korobeynikov.p02dependenciesgraph.other.Repository

@Module
class StorageModule {

    @Provides
    fun provideDatabaseHelper(repository: Repository) = DatabaseHelper(repository)

    @Provides
    fun provideRepository() = Repository()
}