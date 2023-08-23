package ru.korobeynikov.p04mockobjects.presenter

import ru.korobeynikov.p04mockobjects.repository.Order

interface OrderListContract {

    interface View {

        fun showOrders(orders: List<Order>)

        fun showError(message: String?)

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter {
        fun refresh()
    }
}