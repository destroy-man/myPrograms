package ru.korobeynikov.p6hiltcustomcomponent.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import ru.korobeynikov.p6hiltcustomcomponent.other.MyRepository

@InstallIn(MyComponent::class)
@EntryPoint
interface MyEntryPoint {
    fun getMyRepository(): MyRepository
}