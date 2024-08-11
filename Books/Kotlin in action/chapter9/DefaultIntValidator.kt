package ru.korobeynikov.chapter9

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}