package ru.korobeynikov.p02creatingandlaunchlocaltest.calc

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @BeforeEach
    fun setUp() {
        println("\r\nsetUp")
        calculator = Calculator()
    }

    @AfterEach
    fun tearDown() {
        println("tearDown")
    }

    @Test
    fun add() {
        println("add")
        assertEquals(9, calculator.add(6, 3))
    }

    @Test
    fun subtract() {
        println("subtract")
        assertEquals(3, calculator.subtract(6, 3))
    }

    @Test
    fun multiply() {
        println("multiply")
        assertEquals(18, calculator.multiply(6, 3))
    }

    @Test
    fun divide() {
        println("divide")
        assertEquals(2, calculator.divide(6, 3))
    }
}