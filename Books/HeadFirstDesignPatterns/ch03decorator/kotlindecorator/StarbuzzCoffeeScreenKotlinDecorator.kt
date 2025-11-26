package ru.korobeynikov.ch03decorator.kotlindecorator

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch03decorator.kotlindecorator.beverage.Beverage
import ru.korobeynikov.ch03decorator.kotlindecorator.beverage.DarkRoast
import ru.korobeynikov.ch03decorator.kotlindecorator.beverage.Espresso
import ru.korobeynikov.ch03decorator.kotlindecorator.beverage.HouseBlend

@Composable
fun StarbuzzCoffeeScreenKotlinDecorator() {
    //Паттерн Декоратор с использованием функций расширений из Kotlin
    //Функции расширения из Kotlin частный случай реализации паттерна Декоратор

    val beverage: Beverage = Espresso()
    val beverage2: Beverage = DarkRoast().mocha().mocha().whip()
    val beverage3: Beverage = HouseBlend().soy().mocha().whip()

    Column {
        Text("${beverage.description}, $${beverage.cost}")
        Text("${beverage2.description}, $${beverage2.cost}")
        Text("${beverage3.description}, $${beverage3.cost}")
    }
}

private fun Beverage.mocha(): Beverage {
    return this.apply {
        description += ", Mocha"
        cost += .2
    }
}

private fun Beverage.whip(): Beverage {
    return this.apply {
        description += ", Whip"
        cost += .1
    }
}

private fun Beverage.soy(): Beverage {
    return this.apply {
        description += ", Soy"
        cost += .15
    }
}