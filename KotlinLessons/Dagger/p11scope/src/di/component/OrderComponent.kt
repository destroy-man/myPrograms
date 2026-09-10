package ru.korobeynikov.p11scope.di.component

import dagger.Subcomponent
import ru.korobeynikov.p11scope.di.module.OrderModule
import ru.korobeynikov.p11scope.di.scope.OrderScope
import ru.korobeynikov.p11scope.order.OrderRepository

@OrderScope
@Subcomponent(modules = [OrderModule::class])
interface OrderComponent {
    fun getOrderRepository(): OrderRepository
}