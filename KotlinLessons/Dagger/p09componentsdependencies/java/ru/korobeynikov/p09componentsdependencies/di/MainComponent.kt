package ru.korobeynikov.p09componentsdependencies.di

import dagger.Component
import ru.korobeynikov.p09componentsdependencies.other.MainActivityPresenter

@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}