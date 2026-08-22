package ru.korobeynikov.p04inject.main

import android.util.Log
import ru.korobeynikov.p04inject.ServerApi
import ru.korobeynikov.p04inject.Utils
import ru.korobeynikov.p04inject.database.DatabaseHelper
import ru.korobeynikov.p04inject.di.Dev
import javax.inject.Inject
import javax.inject.Named

class MainActivityRepository @Inject constructor(
    private val databaseHelper: DatabaseHelper,
    @Named("prod") private val serverApiProd: ServerApi,
    @Dev private val serverApiDev: ServerApi,
) {
    @Inject //Метод выполнится при инжекте объекта класса
    fun getNetworkUtils(networkUtils: Utils) {
        Log.d("myLogs", "network utils = ${networkUtils.hashCode()}")
    }
}