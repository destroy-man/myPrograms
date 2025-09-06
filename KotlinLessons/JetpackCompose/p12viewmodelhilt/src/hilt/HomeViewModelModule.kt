package ru.korobeynikov.p12viewmodelhilt.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.p12viewmodelhilt.SomeRepository

@Module
@InstallIn(SingletonComponent::class)
class HomeViewModelModule {
    @Provides
    fun provideSomeRepository(): SomeRepository {
        return SomeRepository()
    }
}