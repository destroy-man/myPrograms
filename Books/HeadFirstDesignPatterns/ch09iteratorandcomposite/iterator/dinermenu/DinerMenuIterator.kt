package ru.korobeynikov.ch09iteratorandcomposite.iterator.dinermenu

import ru.korobeynikov.ch09iteratorandcomposite.iterator.Iterator
import ru.korobeynikov.ch09iteratorandcomposite.MenuItem

class DinerMenuIterator(private val items: Array<MenuItem?>) : Iterator {

    var position = 0

    override fun hasNext(): Boolean {
        return !(position >= items.size || items[position] == null)
    }

    override fun next(): MenuItem? {
        val menuItem = items[position]
        position++
        return menuItem
    }
}