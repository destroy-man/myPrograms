package ru.korobeynikov.p1hiltunderthehood.di

import dagger.Component
import ru.korobeynikov.p1hiltunderthehood.other.App

@Component
interface HiltAppComponent {

    fun injectApp(app: App)

    fun getActivityComponent(): HiltActivityComponent
}