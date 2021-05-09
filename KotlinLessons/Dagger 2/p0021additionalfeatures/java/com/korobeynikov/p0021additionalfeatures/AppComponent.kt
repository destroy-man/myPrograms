package com.korobeynikov.p0021additionalfeatures

import dagger.Component

@Component(modules=arrayOf(StorageModule::class))
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
}