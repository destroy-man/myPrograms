package ru.korobeynikov.daggercomponents

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import ru.korobeynikov.mydictionary.MainActivity
import ru.korobeynikov.mydictionary.MainModel
import ru.korobeynikov.mydictionary.MainPresenter

@Module
class MainPresenterModule() {
    @Provides
    fun provideMainPresenter(mainModel: MainModel): MainPresenter {
        return MainPresenter(mainModel)
    }
}