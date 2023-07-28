package ru.korobeynikov.p10componentslivetime.di

import dagger.Component
import ru.korobeynikov.p10componentslivetime.other.MainActivityPresenter

@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}