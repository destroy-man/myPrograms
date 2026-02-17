package ru.korobeynikov.ch12compoundpatterns.observable

class Observable(private val duck: QuackObservable) : QuackObservable {

    val observers = ArrayList<Observer>()

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun notifyObservers(): String {
        val history = StringBuilder()
        observers.forEach { observer ->
            history.append(observer.update(duck))
        }
        return history.toString()
    }
}