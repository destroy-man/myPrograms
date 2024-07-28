package ru.korobeynikov.chapter6.chapter6_1

class Person6_1_5(private val firstName: String, private val lastName: String) {

    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person6_1_5 ?: return false
        return otherPerson.firstName == firstName && otherPerson.lastName == lastName
    }

    override fun hashCode() = firstName.hashCode() * 37 + lastName.hashCode()
}