package ru.korobeynikov.ch09iteratorandcomposite.composite.iterators

import ru.korobeynikov.ch09iteratorandcomposite.composite.menu.MenuComponent

class NullIterator : Iterator<MenuComponent?> {

    override fun hasNext(): Boolean {
        return false
    }

    override fun next(): MenuComponent? {
        return null
    }
}