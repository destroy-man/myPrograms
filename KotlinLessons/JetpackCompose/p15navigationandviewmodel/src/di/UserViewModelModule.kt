package ru.korobeynikov.p15navigationandviewmodel.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.p15navigationandviewmodel.SomeRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserViewModelModule {

    @Provides
    fun provideSomeRepository(): SomeRepository {
        return SomeRepository()
    }

    @Provides
    @Singleton
    @Named("User")
    fun provideSavedStateHandle(): SavedStateHandle {
        return SavedStateHandle()
    }
}