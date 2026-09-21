package ru.korobeynikov.p4entrypoint.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.korobeynikov.p4entrypoint.database.DatabaseHelper

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DatabaseEntryPoint {
    fun getDatabaseHelper(): DatabaseHelper
}