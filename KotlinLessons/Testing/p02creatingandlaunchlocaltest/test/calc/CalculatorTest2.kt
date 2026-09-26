package ru.korobeynikov.p02creatingandlaunchlocaltest.calc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorTest2 {

    lateinit var calculator: Calculator

    @BeforeEach
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun operations() {
        assertEquals(9, calculator.add(6, 3))
        assertEquals(3, calculator.subtract(6, 3))
        assertEquals(18, calculator.multiply(6, 3))
        assertEquals(2, calculator.divide(6, 3))
    }
}