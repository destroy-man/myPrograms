package ru.korobeynikov.p12multiscope.di

import dagger.Component
import ru.korobeynikov.p12multiscope.other.MainActivityPresenter

@Component(modules = [MainModule::class])
interface MainComponent {
    fun getMainActivityPresenter(): MainActivityPresenter
}