package ru.korobeynikov.p11scope.di

import dagger.Subcomponent
import ru.korobeynikov.p11scope.other.OrderRepository

@OrderScope
@Subcomponent(modules = [OrderModule::class])
interface OrderComponent {
    fun getOrderRepository(): OrderRepository
}