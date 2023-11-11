package ru.korobeynikov.p31coroutinestesting

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MyClassTest {

    private lateinit var myClass: MyClass

    @Before
    fun setUp() {
        myClass = MyClass()
    }

    @Test
    fun testSomeMethod() = runTest {
        assertEquals("abc", myClass.someMethod())
    }
}