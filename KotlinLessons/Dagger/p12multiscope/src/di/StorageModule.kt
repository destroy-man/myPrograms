package ru.korobeynikov.p12multiscope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p12multiscope.DatabaseHelper
import ru.korobeynikov.p12multiscope.di.order.OrderComponent
import ru.korobeynikov.p12multiscope.di.user.UserComponent

@Module(subcomponents = [OrderComponent::class, UserComponent::class])
class StorageModule {
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper {
        return DatabaseHelper()
    }
}