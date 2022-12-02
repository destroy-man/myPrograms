package ru.korobeynikov.p02creatingandlaunchlocaltest.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConvertUtilsTest {
    @Test
    fun stringToInteger() {
        assertEquals(2, ConvertUtils.stringToInteger("2"))
        assertEquals(-2, ConvertUtils.stringToInteger("-2"))
        assertEquals(0, ConvertUtils.stringToInteger(""))
        assertEquals(0, ConvertUtils.stringToInteger("a"))
    }
}