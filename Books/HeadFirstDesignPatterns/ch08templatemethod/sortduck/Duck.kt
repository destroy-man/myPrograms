package ru.korobeynikov.ch08templatemethod.sortduck

class Duck(private val name: String, private val weight: Int) : Comparable<Duck> {

    override fun toString(): String {
        return "$name weighs $weight"
    }

    //метод compareTo является частным случаем Шаблонного метода
    override fun compareTo(other: Duck): Int {
        return if (this.weight < other.weight) {
            -1
        } else if (this.weight == other.weight) {
            0
        } else {
            1
        }
    }
}