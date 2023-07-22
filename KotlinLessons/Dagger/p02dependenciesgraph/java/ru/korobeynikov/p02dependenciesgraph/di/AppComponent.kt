package ru.korobeynikov.p02dependenciesgraph.di

import dagger.Component
import ru.korobeynikov.p02dependenciesgraph.other.MainActivityPresenter

@Component(modules = [StorageModule::class, NetworkModule::class, MainModule::class])
interface AppComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}