package ru.korobeynikov.p12viewmodel.di

import dagger.Component
import ru.korobeynikov.p12viewmodel.MainActivity

@Component(modules = [HomeViewModelModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}