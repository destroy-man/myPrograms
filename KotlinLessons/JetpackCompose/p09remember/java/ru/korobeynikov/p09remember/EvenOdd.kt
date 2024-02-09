package ru.korobeynikov.p09remember

class EvenOdd(private val uppercase: Boolean) {

    fun check(value: Int): String {
        var result = if (value % 2 == 0) "even" else "odd"
        if (uppercase) result = result.uppercase()
        return result
    }

    override fun toString() = "EvenOdd(uppercase = $uppercase, ${hashCode().toString(16)})"
}