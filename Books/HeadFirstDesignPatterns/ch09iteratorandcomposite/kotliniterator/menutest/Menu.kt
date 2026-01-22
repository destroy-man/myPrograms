package ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.menutest

import ru.korobeynikov.ch09iteratorandcomposite.MenuItem

interface Menu {
    fun createIterator(): Iterator<MenuItem?>
}