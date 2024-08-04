package ru.korobeynikov.chapter7.chapter7_5

import ru.korobeynikov.chapter7.loadEmails

class Person7_5_2(val name: String) {
    val emails by lazy { loadEmails(this) }
}