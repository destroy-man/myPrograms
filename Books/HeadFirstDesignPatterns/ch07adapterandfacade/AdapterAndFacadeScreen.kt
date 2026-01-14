package ru.korobeynikov.ch07adapterandfacade

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch07adapterandfacade.adapter.IteratorEnumeration
import ru.korobeynikov.ch07adapterandfacade.adapter.TurkeyAdapter
import ru.korobeynikov.ch07adapterandfacade.duck.Duck
import ru.korobeynikov.ch07adapterandfacade.duck.MallardDuck
import ru.korobeynikov.ch07adapterandfacade.hometheater.Amplifier
import ru.korobeynikov.ch07adapterandfacade.hometheater.CdPlayer
import ru.korobeynikov.ch07adapterandfacade.hometheater.DvdPlayer
import ru.korobeynikov.ch07adapterandfacade.hometheater.HomeTheaterFacade
import ru.korobeynikov.ch07adapterandfacade.hometheater.PopcornPopper
import ru.korobeynikov.ch07adapterandfacade.hometheater.Projector
import ru.korobeynikov.ch07adapterandfacade.hometheater.Screen
import ru.korobeynikov.ch07adapterandfacade.hometheater.TheaterLights
import ru.korobeynikov.ch07adapterandfacade.hometheater.Tuner
import ru.korobeynikov.ch07adapterandfacade.turkey.WildTurkey

@Composable
fun FacadeScreen() {
    //Паттерн Фасад
    val movie = "Raiders of the Lost Ark"
    val homeTheater = HomeTheaterFacade(
        amp = Amplifier(),
        tuner = Tuner(),
        dvd = DvdPlayer(),
        cd = CdPlayer(),
        projector = Projector(),
        lights = TheaterLights(),
        screen = Screen(),
        popper = PopcornPopper()
    )
    Text(homeTheater.watchMovie(movie))
    Text(homeTheater.endMovie(movie))
}

@Composable
fun IteratorEnumerationAdapterScreen() {
    val list = listOf(2, 3, 1, 5, 4)
    val adapter = IteratorEnumeration(list.iterator())
    while (adapter.hasMoreElements()) {
        Text(adapter.nextElement().toString())
    }
}

@Composable
fun TurkeyAdapterScreen() {
    //Паттерн Адаптер
    val duck = MallardDuck()
    val turkey = WildTurkey()
    val turkeyAdapter = TurkeyAdapter(turkey)

    Text("The Turkey says...")
    Text(turkey.gobble())
    Text(turkey.fly())

    Text("\nThe Duck says...")
    TestDuck(duck)

    Text("\nThe TurkeyAdapter says...")
    TestDuck(turkeyAdapter)
}

@Composable
fun TestDuck(duck: Duck) {
    Text(duck.quack())
    Text(duck.fly())
}