package ru.korobeynikov.p05mockito

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.*

@ExtendWith(MockitoExtension::class)
class OrderListTest {

    private lateinit var orderList: OrderList

    @Mock
    lateinit var mockObject: OrderRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(mockObject.getByIndex(1)).thenReturn("element 1")
        `when`(mockObject.add("element 1")).thenReturn(true)
        `when`(mockObject.addAll(listOf("element 2", "element 3"))).thenReturn(true)
        orderList = OrderList(mockObject)
    }

    @Test
    fun performAllMethods() {
        orderList.performAllMethods(1, "element 1", listOf("element 2", "element 3"))
        verify(mockObject).setData(anyInt(), eq("element 1"))
    }
}