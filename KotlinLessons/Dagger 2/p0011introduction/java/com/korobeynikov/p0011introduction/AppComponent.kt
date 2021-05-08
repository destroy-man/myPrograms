package com.korobeynikov.p0011introduction

import dagger.Component

@Component(modules=arrayOf(StorageModule::class,NetworkModule::class))
interface AppComponent {
    fun injectsMainActivity(mainActivity:MainActivity)
}