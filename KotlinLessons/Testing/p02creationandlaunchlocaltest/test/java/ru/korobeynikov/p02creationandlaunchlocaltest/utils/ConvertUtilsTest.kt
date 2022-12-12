package ru.korobeynikov.p02creationandlaunchlocaltest.utils

import org.junit.Assert.*
import org.junit.Test

class ConvertUtilsTest {
    @Test
    fun stringToInteger() {
        assertEquals(2, ConvertUtils.stringToInteger("2"))
        assertEquals(-2, ConvertUtils.stringToInteger("-2"))
        assertEquals(0, ConvertUtils.stringToInteger(""))
        assertEquals(0, ConvertUtils.stringToInteger("a"))
    }
}