package ru.korobeynikov.p4entrypoint.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.korobeynikov.p4entrypoint.connection.MyConnection

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DataEntryPoint {
    fun injectMyConnection(myConnection: MyConnection)
}