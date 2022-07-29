package ru.korobeynikov.myachievements.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
class CalendarModule {
    @Provides
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }
}