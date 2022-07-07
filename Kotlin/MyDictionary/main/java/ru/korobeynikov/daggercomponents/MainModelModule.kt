package ru.korobeynikov.daggercomponents

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import ru.korobeynikov.mydictionary.MainModel

@Module
class MainModelModule {
    @Provides
    fun provideMainModel(config:RealmConfiguration): MainModel {
        return MainModel(config)
    }
}