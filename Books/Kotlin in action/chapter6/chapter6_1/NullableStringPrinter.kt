package ru.korobeynikov.chapter6.chapter6_1

class NullableStringPrinter : StringProcessor {
    override fun process(value: String?) = " $value" ?: ""
}