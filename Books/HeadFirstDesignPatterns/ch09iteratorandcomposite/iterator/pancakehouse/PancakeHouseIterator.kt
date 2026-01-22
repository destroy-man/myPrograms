package ru.korobeynikov.ch09iteratorandcomposite.iterator.pancakehouse

import ru.korobeynikov.ch09iteratorandcomposite.iterator.Iterator
import ru.korobeynikov.ch09iteratorandcomposite.MenuItem

class PancakeHouseIterator(private val items: ArrayList<MenuItem?>) : Iterator {

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