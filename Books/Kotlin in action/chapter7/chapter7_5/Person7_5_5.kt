package ru.korobeynikov.chapter7.chapter7_5

class Person7_5_5 {

    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    val name: String by _attributes
}