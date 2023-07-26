package ru.korobeynikov.p07subcomponents.di

import dagger.Subcomponent
import ru.korobeynikov.p07subcomponents.other.DatabaseHelper
import ru.korobeynikov.p07subcomponents.other.MainActivityPresenter

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun getMainActivityPresenter(): MainActivityPresenter

    fun getDatabaseHelper(): DatabaseHelper
}