package ru.korobeynikov.myachievements.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.korobeynikov.myachievements.achievement.MainPresenter
import ru.korobeynikov.myachievements.achievement.MainRepository
import ru.korobeynikov.myachievements.achievement.MainState

@Module
@InstallIn(SingletonComponent::class)
class MainPresenterModule {

    @Provides
    fun provideMainPresenter(mainRepository: MainRepository, mainState: MainState): MainPresenter {
        return MainPresenter(mainRepository, mainState)
    }

    @Provides
    fun provideMainState(): MainState {
        return MainState()
    }
}