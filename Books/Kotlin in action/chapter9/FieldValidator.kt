package ru.korobeynikov.chapter9

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}