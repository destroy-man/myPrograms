package ru.korobeynikov.ch12compoundpatterns

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch12compoundpatterns.decorator.QuackCounter
import ru.korobeynikov.ch12compoundpatterns.ducks.DuckCall
import ru.korobeynikov.ch12compoundpatterns.ducks.MallardDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.ducks.RedheadDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.RubberDuck
import ru.korobeynikov.ch12compoundpatterns.factory.CountingDuckFactory
import ru.korobeynikov.ch12compoundpatterns.goose.Goose
import ru.korobeynikov.ch12compoundpatterns.goose.GooseAdapter
import ru.korobeynikov.ch12compoundpatterns.observable.Quackologist

@Composable
fun DuckSimulatorScreen() {
    //Фабрика + Декоратор + Адаптер + Компоновщик + Наблюдатель
    val duckFactory = CountingDuckFactory()
    val redheadDuck = duckFactory.createRedheadDuck()
    val duckCall = duckFactory.createDuckCall()
    val rubberDuck = duckFactory.createRubberDuck()
    val gooseDuck = GooseAdapter(Goose())

    val flockOfDucks = Flock()

    flockOfDucks.add(redheadDuck)
    flockOfDucks.add(duckCall)
    flockOfDucks.add(rubberDuck)
    flockOfDucks.add(gooseDuck)

    val flockOfMallards = Flock()

    val mallardOne = duckFactory.createMallardDuck()
    val mallardTwo = duckFactory.createMallardDuck()
    val mallardThree = duckFactory.createMallardDuck()
    val mallardFour = duckFactory.createMallardDuck()

    flockOfMallards.add(mallardOne)
    flockOfMallards.add(mallardTwo)
    flockOfMallards.add(mallardThree)
    flockOfMallards.add(mallardFour)

    flockOfDucks.add(flockOfMallards)
    Column {
        Text("\nDuck Simulator: With Observer")
        val quackologist = Quackologist()
        flockOfDucks.registerObserver(quackologist)

        Text(simulate(flockOfDucks))

        Text("The ducks quacked ${QuackCounter.getQuacks()} times")
    }
}

@Composable
fun DuckSimulatorScreenAdapterAbstractFactoryComposite() {
    val duckFactory = CountingDuckFactory()
    val redheadDuck = duckFactory.createRedheadDuck()
    val duckCall = duckFactory.createDuckCall()
    val rubberDuck = duckFactory.createRubberDuck()
    val gooseDuck = GooseAdapter(Goose())

    val flockOfDucks = Flock()

    flockOfDucks.add(redheadDuck)
    flockOfDucks.add(duckCall)
    flockOfDucks.add(rubberDuck)
    flockOfDucks.add(gooseDuck)

    val flockOfMallards = Flock()

    val mallardOne = duckFactory.createMallardDuck()
    val mallardTwo = duckFactory.createMallardDuck()
    val mallardThree = duckFactory.createMallardDuck()
    val mallardFour = duckFactory.createMallardDuck()

    flockOfMallards.add(mallardOne)
    flockOfMallards.add(mallardTwo)
    flockOfMallards.add(mallardThree)
    flockOfMallards.add(mallardFour)

    flockOfDucks.add(flockOfMallards)
    Column {
        Text("\nDuck Simulator: Whole Flock Simulation")

        Text(simulate(flockOfDucks))
        Text("\nDuck Simulator: Mallard Flock Simulation")
        Text(simulate(flockOfMallards))

        Text("The ducks quacked ${QuackCounter.getQuacks()} times")
    }
}

@Composable
fun DuckSimulatorScreenAdapterAbstractFactory() {
    val duckFactory = CountingDuckFactory()
    val mallardDuck = duckFactory.createMallardDuck()
    val redheadDuck = duckFactory.createRedheadDuck()
    val duckCall = duckFactory.createDuckCall()
    val rubberDuck = duckFactory.createRubberDuck()
    val gooseDuck = GooseAdapter(Goose())
    Column {
        Text("\nDuck Simulator: With Abstract Factory")

        Text(simulate(mallardDuck))
        Text(simulate(redheadDuck))
        Text(simulate(duckCall))
        Text(simulate(rubberDuck))
        Text(simulate(gooseDuck))

        Text("The ducks quacked ${QuackCounter.getQuacks()} times")
    }
}

@Composable
fun DuckSimulatorScreenAdapterDecorator() {
    val mallardDuck = QuackCounter(MallardDuck())
    val redheadDuck = QuackCounter(RedheadDuck())
    val duckCall = QuackCounter(DuckCall())
    val rubberDuck = QuackCounter(RubberDuck())
    val gooseDuck = GooseAdapter(Goose())
    Column {
        Text("\nDuck Simulator: With Decorator")

        Text(simulate(mallardDuck))
        Text(simulate(redheadDuck))
        Text(simulate(duckCall))
        Text(simulate(rubberDuck))
        Text(simulate(gooseDuck))

        Text("The ducks quacked ${QuackCounter.getQuacks()} times")
    }
}

@Composable
fun DuckSimulatorScreenAdapter() {
    val mallardDuck = MallardDuck()
    val redheadDuck = RedheadDuck()
    val duckCall = DuckCall()
    val rubberDuck = RubberDuck()
    val gooseDuck = GooseAdapter(Goose())
    Column {
        Text("\nDuck Simulator: With Goose Adapter")

        Text(simulate(mallardDuck))
        Text(simulate(redheadDuck))
        Text(simulate(duckCall))
        Text(simulate(rubberDuck))
        Text(simulate(gooseDuck))
    }
}

@Composable
fun DuckSimulatorScreenWithoutPatterns() {
    val mallardDuck = MallardDuck()
    val redheadDuck = RedheadDuck()
    val duckCall = DuckCall()
    val rubberDuck = RubberDuck()
    Column {
        Text("\nDuck Simulator")

        Text(simulate(mallardDuck))
        Text(simulate(redheadDuck))
        Text(simulate(duckCall))
        Text(simulate(rubberDuck))
    }
}

fun simulate(duck: Quackable): String {
    return duck.quack()
}