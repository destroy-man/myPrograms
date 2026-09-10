package ru.korobeynikov.p11scope.di.module

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.di.scope.OrderScope
import ru.korobeynikov.p11scope.order.OrderRepository

@Module
class OrderModule {
    @OrderScope
    @Provides
    fun provideOrderRepository(): OrderRepository {
        return OrderRepository()
    }
}