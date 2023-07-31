package ru.korobeynikov.p13assistedinject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13assistedinject.other.MainActivityPresenter

@Module
class MainModule {
    @Provides
    fun provideMainActivityPresenter(serverApiFactory: ServerApiFactory) =
        MainActivityPresenter(serverApiFactory)
}