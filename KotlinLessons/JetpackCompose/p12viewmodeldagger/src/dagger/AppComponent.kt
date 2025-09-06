package ru.korobeynikov.p12viewmodel.dagger

import dagger.Component
import ru.korobeynikov.p12viewmodel.MainActivity

@Component(modules = [HomeViewModelModule::class])
interface AppComponent {
    fun injectMainActivity(activity: MainActivity)
}