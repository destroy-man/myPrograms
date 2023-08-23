package ru.korobeynikov.p04mockobjects.presenter

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p04mockobjects.repository.OrderRepository

class OrderListPresenter(
    private val orderRepository: OrderRepository,
    private val workScheduler: Scheduler,
    private val resultScheduler: Scheduler,
) : BasePresenter<OrderListContract.View>(), OrderListContract.Presenter {
    override fun refresh() {
        if (getView() == null) return
        getView()?.showProgress()
        orderRepository.getOrders().subscribeOn(workScheduler).observeOn(resultScheduler).subscribeBy(
            onNext = { orders ->
                if (getView() == null)
                    return@subscribeBy
                getView()?.hideProgress()
                getView()?.showOrders(orders)
            },
            onError = { e ->
                if (getView() == null)
                    return@subscribeBy
                getView()?.hideProgress()
                getView()?.showError(e.message)
            }
        )
    }
}