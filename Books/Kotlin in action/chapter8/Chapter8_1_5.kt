package ru.korobeynikov.chapter8

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Объявление функций высшего порядка
//Возврат функций из функций
@Composable
fun Chapter8_1_5() {
    val contacts = listOf(
        Person8_1_5("Dmitry", "Jemerov", "123-4567"),
        Person8_1_5("Svetlana", "Isakova", null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    Text(text = " ${contacts.filter(contactListFilters.getPredicate())}")
}

fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED)
        return { order ->
            6 + 2.1 * order.itemCount
        }
    return { order ->
        1.2 * order.itemCount
    }
}