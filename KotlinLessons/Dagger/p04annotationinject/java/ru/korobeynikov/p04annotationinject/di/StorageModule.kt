package ru.korobeynikov.p04annotationinject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p04annotationinject.other.Repository

@Module
class StorageModule {
    @Provides
    fun provideRepository() = Repository()
}