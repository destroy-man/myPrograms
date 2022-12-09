package ru.korobeynikov.p05mockito

interface OrderRepository {
    fun getByIndex(index: Int): String
    fun add(order: String?): Boolean
    fun addAll(listOrders: List<String>): Boolean
    fun setData(index: Int, description: String?)
}