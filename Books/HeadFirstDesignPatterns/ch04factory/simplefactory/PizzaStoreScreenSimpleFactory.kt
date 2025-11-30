package ru.korobeynikov.ch04factory.simplefactory

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PizzaStoreScreenSimpleFactory() {
    //Паттерн Фабрика
    val pizzaStore = PizzaStore(SimplePizzaFactory())
    val pizza = pizzaStore.orderPizza("pepperoni")
    if (pizza != null) Text(pizza.history.toString())
    else Text("Нужного типа пиццы нет!")
}