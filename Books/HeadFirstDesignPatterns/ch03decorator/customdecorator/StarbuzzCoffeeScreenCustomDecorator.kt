package ru.korobeynikov.ch03decorator.customdecorator

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage
import ru.korobeynikov.ch03decorator.customdecorator.beverage.DarkRoast
import ru.korobeynikov.ch03decorator.customdecorator.beverage.Espresso
import ru.korobeynikov.ch03decorator.customdecorator.beverage.HouseBlend
import ru.korobeynikov.ch03decorator.customdecorator.decorator.Mocha
import ru.korobeynikov.ch03decorator.customdecorator.decorator.Soy
import ru.korobeynikov.ch03decorator.customdecorator.decorator.Whip

@Composable
fun StarbuzzCoffeeScreenCustomDecorator() {
    //Паттерн Декоратор кастомная реализация
    val beverage: Beverage = Espresso()

    var beverage2: Beverage = DarkRoast()
    beverage2 = Mocha(beverage2)
    beverage2 = Mocha(beverage2)
    beverage2 = Whip(beverage2)

    var beverage3: Beverage = HouseBlend()
    beverage3 = Soy(beverage3)
    beverage3 = Mocha(beverage3)
    beverage3 = Whip(beverage3)

    Column {
        Text("${beverage.description} $${beverage.cost()}")
        Text("${beverage2.description} $${beverage2.cost()}")
        Text("${beverage3.description} $${beverage3.cost()}")
    }
}