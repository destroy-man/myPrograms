package ru.korobeynikov.p5customcomponent.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import ru.korobeynikov.p5customcomponent.MyRepository

@InstallIn(MyComponent::class)
@EntryPoint
interface MyEntryPoint {
    fun getMyRepository(): MyRepository
}