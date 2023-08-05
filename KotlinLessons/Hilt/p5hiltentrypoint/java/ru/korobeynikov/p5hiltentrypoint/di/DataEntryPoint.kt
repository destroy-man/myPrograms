package ru.korobeynikov.p5hiltentrypoint.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.korobeynikov.p5hiltentrypoint.other.MyConnection

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DataEntryPoint {

    fun injectMyConnection(myConnection: MyConnection)

    fun getMyConnection(): MyConnection
}