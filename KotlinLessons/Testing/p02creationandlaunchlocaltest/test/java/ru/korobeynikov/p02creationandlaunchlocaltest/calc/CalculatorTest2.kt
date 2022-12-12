package ru.korobeynikov.p02creationandlaunchlocaltest.calc

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculatorTest2 {

    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun operations() {
        assertEquals(3, calculator.add(1, 2))
        assertEquals(1, calculator.add(1, 0))
        assertEquals(1, calculator.subtract(7, 6))
        assertEquals(-2, calculator.subtract(0, 2))
        assertEquals(12, calculator.multiply(3, 4))
        assertEquals(0, calculator.multiply(9, 0))
        assertEquals(2, calculator.divide(8, 4))
        assertEquals(0, calculator.divide(5, 0))
    }
}