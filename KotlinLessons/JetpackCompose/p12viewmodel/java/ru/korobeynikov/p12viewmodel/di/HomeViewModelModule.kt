package ru.korobeynikov.p12viewmodel.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12viewmodel.SomeRepository

@Module
class HomeViewModelModule {
    @Provides
    fun provideSomeRepository() = SomeRepository()
}