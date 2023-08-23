package ru.korobeynikov.p04mockobjects.repository

import io.reactivex.rxjava3.core.Observable

interface OrderRepository {
    fun getOrders(): Observable<List<Order>>
}