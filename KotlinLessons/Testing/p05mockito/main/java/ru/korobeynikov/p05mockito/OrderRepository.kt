package ru.korobeynikov.p05mockito

interface OrderRepository {
    fun getByIndex(index: Int): String
    fun add(order: String?): Boolean
    fun addAll(orders: List<Order>): Boolean
    fun setData(index: Int, order: String?)
}