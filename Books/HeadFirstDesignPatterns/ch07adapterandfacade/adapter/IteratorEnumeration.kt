package ru.korobeynikov.ch07adapterandfacade.adapter

import java.util.Enumeration

class IteratorEnumeration(private val iterator: Iterator<Any>) : Enumeration<Any> {

    override fun hasMoreElements(): Boolean {
        return iterator.hasNext()
    }

    override fun nextElement(): Any {
        return iterator.next()
    }
}