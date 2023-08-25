package ru.korobeynikov.p05mockito

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MockTest {

    @Mock
    lateinit var mockObject: MockObject

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test() {
        mockObject.setData(1, "element 1")
        verify(mockObject).setData(anyInt(), ArgumentMatchers.eq("element 1"))
    }
}