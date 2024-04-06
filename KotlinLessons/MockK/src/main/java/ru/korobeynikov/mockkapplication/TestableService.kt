package ru.korobeynikov.mockkapplication

class TestableService {

    fun getDataFromDB(testParameter: String) = "Expected Output"

    fun doSomethingElse(testParameter: String) = "I don't want to!"

    fun addHelloWorld(strList: MutableList<String>) {
        println("addHelloWorld() is called")
        strList += "Hello World!"
    }
}