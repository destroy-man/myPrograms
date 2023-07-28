package ru.korobeynikov.p11scope.di

import dagger.Component
import ru.korobeynikov.p11scope.other.MainActivityPresenter

@Component(modules = [MainModule::class])
interface MainComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}