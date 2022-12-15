package ru.korobeynikov.p05mockito

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner::class)
class OrderListPresenterTest {

    private lateinit var orderListPresenter: OrderListPresenter

    @Mock
    lateinit var mockObject: OrderRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val order2 = Order(2, 200f, "element 2")
        val order3 = Order(3, 300f, "element 3")
        `when`(mockObject.getByIndex(1)).thenReturn("element 1")
        `when`(mockObject.add("element 1")).thenReturn(true)
        `when`(mockObject.addAll(listOf(order2, order3))).thenReturn(true)
        orderListPresenter = OrderListPresenter(mockObject)
    }

    @Test
    fun testMockito() {
        val order2 = Order(2, 200f, "element 2")
        val order3 = Order(3, 300f, "element 3")
        orderListPresenter.performAllMethods(1, "element 1", listOf(order2, order3))
        verify(mockObject).setData(anyInt(), ArgumentMatchers.eq("element 1"))
    }
}