package ru.korobeynikov.p12junitexceptionsparameterizedtimeoutignored

import org.junit.Assert.*

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ConverterTest(private val inputValue: String, private val expectedValue: Int) {

    private lateinit var converter: Converter

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Test {index}: stringToInteger({0}) = {1}")
        fun data(): Collection<Any> {
            return arrayListOf(
                arrayOf("1", 1),
                arrayOf("-1", -1),
                arrayOf("0", 0),
                arrayOf("1234", 1234)
            )
        }
    }

    @Before
    fun setUp() {
        converter = Converter()
    }

    @Ignore
    @Test
    fun parseNumbersToInteger() {
        assertEquals(expectedValue, converter.stringToInteger(inputValue))
    }
}