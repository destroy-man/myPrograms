package ru.korobeynikov.chapter4.chapter4_4

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}