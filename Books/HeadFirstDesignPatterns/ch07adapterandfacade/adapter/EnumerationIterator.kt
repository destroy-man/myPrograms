package ru.korobeynikov.ch07adapterandfacade.adapter

import java.util.Enumeration

class EnumerationIterator(private val enumeration: Enumeration<Any>) : Iterator<Any> {

    override fun hasNext(): Boolean {
        return enumeration.hasMoreElements()
    }

    override fun next(): Any {
        return enumeration.nextElement()
    }
}