package ru.korobeynikov.ch04factory.factorymethod

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch04factory.factorymethod.chicagopizza.ChicagoStylePizzaStore
import ru.korobeynikov.ch04factory.factorymethod.nypizza.NYStylePizzaStore

@Composable
fun PizzaStoreScreenFactoryMethod() {
    //Паттерн Фабричный метод
    val nyStore = NYStylePizzaStore()
    val chicagoStore = ChicagoStylePizzaStore()

    Column {
        PizzaHistory(nyStore, "cheese")
        Text("Ethan ordered a NY Style Sauce and Cheese Pizza\n")
        PizzaHistory(chicagoStore, type = "cheese")
        Text("Joel ordered a Chicago Style Deep Dish Cheese Pizza")
    }
}

@Composable
private fun PizzaHistory(store: PizzaStore, type: String) {
    val pizza = store.orderPizza(type)
    if (pizza != null) Text(pizza.history.toString())
    else Text("Нужного типа пиццы нет!")
}