package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p08subcomponentsbuilderfactory.database.DatabaseHelper

@Module(
    //Для Inject Subcomponent.Builder
    subcomponents = [MainComponent::class]
)
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}