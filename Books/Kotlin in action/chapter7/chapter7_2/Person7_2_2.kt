package ru.korobeynikov.chapter7.chapter7_2

class Person7_2_2(private val firstName: String, private val lastName: String) : Comparable<Person7_2_2> {
    override fun compareTo(other: Person7_2_2) =
        compareValuesBy(this, other, Person7_2_2::lastName, Person7_2_2::firstName)
}