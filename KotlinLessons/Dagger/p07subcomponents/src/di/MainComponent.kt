package ru.korobeynikov.p07subcomponents.di

import dagger.Subcomponent
import ru.korobeynikov.p07subcomponents.database.DatabaseHelper
import ru.korobeynikov.p07subcomponents.main.MainActivityRepository

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun getMainActivityRepository(): MainActivityRepository

    fun getDatabaseHelper(): DatabaseHelper
}