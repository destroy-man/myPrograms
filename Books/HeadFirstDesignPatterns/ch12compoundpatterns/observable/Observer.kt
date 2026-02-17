package ru.korobeynikov.ch12compoundpatterns.observable

interface Observer {
    fun update(duck: QuackObservable): String
}