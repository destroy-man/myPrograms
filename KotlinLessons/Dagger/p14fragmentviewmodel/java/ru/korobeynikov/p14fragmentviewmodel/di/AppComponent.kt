package ru.korobeynikov.p14fragmentviewmodel.di

import dagger.Component

@Component
interface AppComponent {
    fun getMainComponent(): MainComponent
}