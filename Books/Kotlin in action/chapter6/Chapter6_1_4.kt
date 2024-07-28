package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_1.Address
import ru.korobeynikov.chapter6.chapter6_1.Company
import ru.korobeynikov.chapter6.chapter6_1.Person6_1_3

//Поддержка значения null
//Оператор "Элвис" : "?:"
@Composable
fun Chapter6_1_4() {
    val address = Address("Elsestr. 47", 80687, "Munich", "Germany")
    val jetbrains = Company("JetBrains", address)
    val person = Person6_1_3("Dmitry", jetbrains)
    Text(text = printShippingLabel(person))
}

fun printShippingLabel(person: Person6_1_3): String {
    val address = person.company?.address ?: throw IllegalArgumentException("No address")
    return with(address) {
        " $streetAddress\n $zipCode $city, $country"
    }
}