package ru.korobeynikov.p04mockk.testableservice

import android.util.Log

class TestableService {

    fun getDataFromDb(testParameter: String): String {
        return "data"
    }

    fun doSomethingElse(testParameter: String): String {
        return "I don't want to!"
    }

    fun addHelloWorld(strList: MutableList<String>) {
        Log.d("myLogs", "addHelloWorld() is called")
        strList += "Hello World!"
    }
}