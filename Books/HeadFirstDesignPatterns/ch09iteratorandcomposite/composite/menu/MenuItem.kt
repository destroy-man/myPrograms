package ru.korobeynikov.ch09iteratorandcomposite.composite.menu

import ru.korobeynikov.ch09iteratorandcomposite.composite.iterators.NullIterator

class MenuItem(
    override val name: String,
    override val description: String,
    override val vegetarian: Boolean,
    override val price: Double
) : MenuComponent(name, description, vegetarian, price) {

    override fun print(): String {
        val history = StringBuilder()
        history.append(" $name")
        if (vegetarian) {
            history.append("(v)")
        }
        history.appendLine(", $price")
        history.appendLine("   -- $description")
        return history.toString()
    }

    override fun createIterator(): Iterator<MenuComponent?> {
        return NullIterator()
    }
}