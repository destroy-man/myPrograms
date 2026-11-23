package ru.korobeynikov.p28jetpacknavigationviewmodel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.p28jetpacknavigationviewmodel.viewmodel.ScreenRepository

@Module
@InstallIn(SingletonComponent::class)
class ScreenModule {
    @Provides
    fun provideScreenRepository(): ScreenRepository {
        return ScreenRepository()
    }
}