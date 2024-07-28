package ru.korobeynikov.chapter6.chapter6_1

class StringPrinter : StringProcessor {
    override fun process(value: String) = " $value"
}