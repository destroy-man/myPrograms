package ru.korobeynikov.p11scope.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.other.OrderRepository

@Module
class OrderModule {
    @OrderScope
    @Provides
    fun provideOrderRepository() = OrderRepository()
}