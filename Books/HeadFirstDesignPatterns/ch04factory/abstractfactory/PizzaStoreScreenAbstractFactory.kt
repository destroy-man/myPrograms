package ru.korobeynikov.ch04factory.abstractfactory

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PizzaStoreScreenAbstractFactory() {
    //Паттерн Абстрактная фабрика
    val nyStore = NYPizzaStore()
    PizzaHistory(nyStore, "cheese")
}

@Composable
private fun PizzaHistory(store: PizzaStore, type: String) {
    val pizza = store.orderPizza(type)
    if (pizza != null) Text(pizza.toString())
    else Text("Нужного типа пиццы нет!")
}