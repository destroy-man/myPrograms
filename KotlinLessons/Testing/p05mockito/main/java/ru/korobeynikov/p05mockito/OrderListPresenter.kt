package ru.korobeynikov.p05mockito

class OrderListPresenter(private val orderRepository: OrderRepository) {

    private fun getByIndex(index: Int): String {
        return orderRepository.getByIndex(index)
    }

    private fun add(order: String?): Boolean {
        return orderRepository.add(order)
    }

    private fun addAll(orders: List<Order>): Boolean {
        return orderRepository.addAll(orders)
    }

    private fun setData(index: Int, order: String?) {
        orderRepository.setData(index, order)
    }

    fun performAllMethods(index: Int, order: String?, orders: List<Order>) {
        getByIndex(index)
        add(order)
        addAll(orders)
        setData(index, order)
    }
}