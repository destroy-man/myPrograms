package ru.korobeynikov.p05mockito

class OrderList(private val orderRepository: OrderRepository) {
    fun performAllMethods(index: Int, order: String?, listOrders: List<String>) {
        orderRepository.getByIndex(index)
        orderRepository.add(order)
        orderRepository.addAll(listOrders)
        orderRepository.setData(index, order)
    }
}