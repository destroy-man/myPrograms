package ru.korobeynikov.ch09iteratorandcomposite.iterator

interface Iterator {

    fun hasNext(): Boolean

    fun next(): Any?
}