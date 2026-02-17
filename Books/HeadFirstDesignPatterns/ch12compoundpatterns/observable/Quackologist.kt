package ru.korobeynikov.ch12compoundpatterns.observable

class Quackologist : Observer {
    override fun update(duck: QuackObservable): String {
        return "Quackologist: $duck just quacked.\n"
    }
}