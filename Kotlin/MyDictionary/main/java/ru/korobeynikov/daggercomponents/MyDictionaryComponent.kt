package ru.korobeynikov.daggercomponents

import dagger.Component
import ru.korobeynikov.mydictionary.MainActivity

@Component(modules = [RealmConfigModule::class, CoroutineScopeModule::class, MainPresenterModule::class, MainModelModule::class])
interface MyDictionaryComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}