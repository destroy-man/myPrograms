package ru.korobeynikov.p12junitexceptionsparameterizedtimeoutignored

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ConverterTest(private val inputValue: String, private val expectedValue: Int) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Test {index}: stringToInt({0}) = {1}")
        fun data() =
            arrayListOf(arrayOf("1", 1), arrayOf("-1", -1), arrayOf("0", 0), arrayOf("1234", 1234)).toList()
    }

    private lateinit var converter: Converter

    @Before
    fun setUp() {
        converter = Converter()
    }

    @Ignore("checking ignore")
    @Test
    fun parseNumbersToInt() {
        assertEquals(expectedValue, converter.stringToInt(inputValue))
    }
}