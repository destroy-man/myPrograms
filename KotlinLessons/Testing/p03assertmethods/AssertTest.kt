package ru.korobeynikov.p03assertmethods

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class AssertTest {

    @Test
    fun assertEqualsInt() {
        val a = 1
        val b = 2
        assertEquals(a, b)
    }

    @Test
    fun assertEqualsDouble() {
        val c = 2.05 - 0.73
        assertEquals(1.32, c, 0.0001)
    }

    @Test
    fun assertEqualsObjects() {
        val a = "abc"
        val b = "abc"
        assertEquals(a, b)
    }

    @Test
    fun assertNotEqualsMethod() {
        val a = 1
        val b = 2
        assertNotEquals(a, b)
    }

    @Test
    fun assertTrueOrFalse() {
        val b = true
        assertTrue(b)
        assertFalse(b)
    }

    @Test
    fun assertArrayEqualsMethod() {
        val array1 = arrayOf(1, 2, 3)
        val array2 = arrayOf(1, 2, 3)
        assertArrayEquals(array1, array2)
    }

    @Test
    fun assertNullOrNotNull() {
        val obj: Any? = null
        assertNull(obj)
        assertNotNull(obj)
    }

    @Test
    fun assertSameOrNotSame() {
        val s1 = "123"
        val s2 = "123"
        assertSame(s1, s2)
        assertNotSame(s1, s2)
    }

    @Test
    fun failMethod() {
        fail<Int>()
    }

    @Test
    fun assertEqualsWithMessage() {
        val a = 1
        val b = 2
        assertEquals(a, b, "Values are not equal: $a and $b")
    }
}