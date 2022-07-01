package ru.korobeynikov.mydictionary

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration

@Module
class RealmConfigModule {
    @Provides
    fun provideRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder().schemaVersion(1L).build()
    }
}