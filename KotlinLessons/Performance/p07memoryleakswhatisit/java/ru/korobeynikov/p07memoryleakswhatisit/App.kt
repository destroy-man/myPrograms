package ru.korobeynikov.p07memoryleakswhatisit

import android.app.Application

class App : Application() {

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        repository = Repository()
    }
}