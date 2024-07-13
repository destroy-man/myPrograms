package ru.korobeynikov.chapter4.chapter4_1

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}