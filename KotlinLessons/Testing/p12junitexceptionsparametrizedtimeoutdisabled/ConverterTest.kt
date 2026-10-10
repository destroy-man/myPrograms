package ru.korobeynikov.p12junitexceptionsparametrizedtimeoutdisabled

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class ConverterTest {

    lateinit var converter: Converter

    @BeforeEach
    fun setUp() {
        converter = Converter()
    }

    @Test
    fun exceptionExample() {
        assertThrows<NumberFormatException> {
            converter.stringToInteger("a")
        }
    }

    @ParameterizedTest(name = "Parameterized test {index}: stringToInteger({0}) = {1}")
    @MethodSource("data")
    fun parametrizedExample(expected: Int, checkedData: String) {
        assertEquals(expected, converter.stringToInteger(checkedData))
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    fun timeoutExample() {
        assertEquals(1, converter.stringToInteger("1"))
    }

    @Test
    @Disabled
    fun disabledExample() {
        assertEquals(-1, converter.stringToInteger("-1"))
    }

    companion object {
        @JvmStatic
        fun data(): Stream<Arguments?>? = Stream.of(
            Arguments.of(1, "1"),
            Arguments.of(-1, "-1"),
            Arguments.of(0, "0"),
            Arguments.of(1234, "1234")
        )
    }
}