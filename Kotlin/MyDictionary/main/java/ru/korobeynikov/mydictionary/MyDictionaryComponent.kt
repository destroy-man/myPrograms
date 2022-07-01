package ru.korobeynikov.mydictionary

import dagger.Component

@Component(modules = [RealmConfigModule::class, CoroutineScopeModule::class])
interface MyDictionaryComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}