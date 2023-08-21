package ru.korobeynikov.p02creatingandlaunchlocaltest.utils

import org.junit.Assert.*
import org.junit.Test

class ConvertUtilsTest {
    @Test
    fun stringToInt() {
        assertEquals(2, ConvertUtils.stringToInt("2"))
        assertEquals(-2, ConvertUtils.stringToInt("-2"))
        assertEquals(0, ConvertUtils.stringToInt(""))
        assertEquals(0, ConvertUtils.stringToInt("a"))
    }
}