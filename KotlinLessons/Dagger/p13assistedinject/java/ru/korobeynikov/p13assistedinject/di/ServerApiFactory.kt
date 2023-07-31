package ru.korobeynikov.p13assistedinject.di

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.korobeynikov.p13assistedinject.other.ServerApi

@AssistedFactory
interface ServerApiFactory {
    fun create(@Assisted("host") host: String, @Assisted("port") port: String = "80"): ServerApi
}