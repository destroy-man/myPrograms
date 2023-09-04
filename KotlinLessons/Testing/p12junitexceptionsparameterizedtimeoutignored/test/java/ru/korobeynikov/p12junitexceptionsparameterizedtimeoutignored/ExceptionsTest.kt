package ru.korobeynikov.p12junitexceptionsparameterizedtimeoutignored

import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class ExceptionsTest {

    private lateinit var converter: Converter

    @Before
    fun setUp() {
        converter = Converter()
    }

    @Test
    fun test() {
        assertThrows(NumberFormatException::class.java) {
            println("For input string: \"a\"")
        }
        converter.stringToInt("a")
    }
}