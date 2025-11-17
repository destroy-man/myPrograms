package ru.korobeynikov.ch01introduction.strategy.duck

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch01introduction.strategy.fly.FlyRocketPowered

@Composable
fun MiniDuckSimulatorScreen() {
    //Паттерн Стратегия
    val mallard: Duck = MallardDuck()
    val model: Duck = ModelDuck()
    Column {
        Text(text = mallard.performQuack())
        Text(text = mallard.performFly())
        Text(text = model.performFly())
        model.flyBehavior = FlyRocketPowered()
        Text(text = model.performFly())
    }
}