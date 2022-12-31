package ru.korobeynikov.p12junitexceptionsparameterizedtimeoutignored

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ExceptionsTest {

    private lateinit var converter: Converter

    @Rule
    @JvmField
    val thrown: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        converter = Converter()
    }

    @Test
    fun test() {
        thrown.expect(NumberFormatException::class.java)
        thrown.expectMessage("For input string: \"a\"")
        converter.stringToInteger("a")
    }
}