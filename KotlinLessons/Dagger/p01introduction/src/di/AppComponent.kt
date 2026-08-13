package ru.korobeynikov.p01introduction.di

import dagger.Component
import ru.korobeynikov.p01introduction.DatabaseHelper
import ru.korobeynikov.p01introduction.MainActivity
import ru.korobeynikov.p01introduction.NetworkUtils

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class])
interface AppComponent {

    /*Внедрение зависимости через get методы
    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils
    */

    //Внедрение зависимости через inject метод
    fun injectMainActivity(mainActivity: MainActivity)
}