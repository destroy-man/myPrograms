package ru.korobeynikov.chapter6.chapter6_2

data class Person6_2_2(
    val name: String,
    val age: Int? = null,
) {
    fun isOlderThan(other: Person6_2_2): Boolean? {
        if (age == null || other.age == null)
            return null
        return age > other.age
    }
}
