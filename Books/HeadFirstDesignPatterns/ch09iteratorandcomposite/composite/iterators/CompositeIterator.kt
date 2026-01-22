package ru.korobeynikov.ch09iteratorandcomposite.composite.iterators

import ru.korobeynikov.ch09iteratorandcomposite.composite.menu.MenuComponent
import java.util.Stack

class CompositeIterator(iterator: Iterator<MenuComponent?>) : Iterator<MenuComponent?> {

    val stack = Stack<Iterator<MenuComponent?>>()

    init {
        stack.push(iterator)
    }

    override fun hasNext(): Boolean {
        return if (stack.empty()) {
            false
        } else {
            val iterator = stack.peek()
            if (!iterator.hasNext()) {
                stack.pop()
                return hasNext()
            } else {
                true
            }
        }
    }

    override fun next(): MenuComponent? {
        return if (hasNext()) {
            val iterator = stack.peek()
            val component = iterator.next()
            stack.push(component?.createIterator())
            component
        } else {
            null
        }
    }
}