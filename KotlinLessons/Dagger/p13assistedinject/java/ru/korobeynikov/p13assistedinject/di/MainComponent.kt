package ru.korobeynikov.p13assistedinject.di

import dagger.Component
import ru.korobeynikov.p13assistedinject.other.MainActivityPresenter

@Component(modules = [MainModule::class])
interface MainComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}