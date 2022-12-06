package ru.korobeynikov.p04mockobjects

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import org.mockito.Mockito.*

@ExtendWith(MockitoExtension::class)
class OrderListPresenterTest {

    private lateinit var presenter: OrderListPresenter

    @Mock
    lateinit var mockOrderRepository: OrderRepository

    @Mock
    lateinit var mockView: OrderListContract.View

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = OrderListPresenter(mockOrderRepository, Schedulers.trampoline(), Schedulers.trampoline())
        presenter.attachView(mockView)
    }

    @Test
    fun refreshSuccess() {
        val fakeOrders = getFakeOrderList()
        `when`(mockOrderRepository.getOrders()).thenReturn(Observable.just(fakeOrders))
        presenter.refresh()
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).showOrders(fakeOrders)
        verify(mockView, never()).showError(anyString())
    }

    @Test
    fun refreshFailed() {
        val error = "Network error"
        `when`(mockOrderRepository.getOrders()).thenReturn(Observable.error(Exception(error)))
        presenter.refresh()
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).showError(error)
        verify(mockView, never()).showOrders(ArgumentMatchers.anyList())
    }

    @Test
    fun refreshWithoutView() {
        presenter.detachView()
        presenter.refresh()
        verify(mockOrderRepository, never()).getOrders()
        verify(mockView, never()).showProgress()
        verify(mockView, never()).showOrders(ArgumentMatchers.anyList())
    }

    private fun getFakeOrderList(): List<Order> {
        val orders = LinkedList<Order>()
        orders.add(Order(1, 100f, "Order 1"))
        orders.add(Order(2, 200f, "Order 2"))
        return orders
    }
}