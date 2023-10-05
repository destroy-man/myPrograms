package ru.korobeynikov.p09memoryleaksleakcanary

import android.app.Application

class App : Application() {

    private lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        repository = Repository()
    }

    fun getRepository() = repository
}