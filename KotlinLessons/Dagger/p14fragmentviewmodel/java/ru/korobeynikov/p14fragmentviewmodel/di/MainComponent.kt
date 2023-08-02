package ru.korobeynikov.p14fragmentviewmodel.di

import dagger.Subcomponent
import ru.korobeynikov.p14fragmentviewmodel.other.ViewModelFactory

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun getViewModelFactory(): ViewModelFactory
}