package ru.korobeynikov.ch09iteratorandcomposite.composite.menu

import ru.korobeynikov.ch09iteratorandcomposite.composite.iterators.CompositeIterator

class Menu(override val name: String, override val description: String) :
    MenuComponent(name, description) {

    lateinit var iterator: Iterator<MenuComponent?>
    val menuComponents = ArrayList<MenuComponent>()

    override fun add(menuComponent: MenuComponent) {
        menuComponents.add(menuComponent)
    }

    override fun remove(menuComponent: MenuComponent) {
        menuComponents.remove(menuComponent)
    }

    override fun getChild(i: Int): MenuComponent {
        return menuComponents[i]
    }

    override fun print(): String {
        val history = StringBuilder()
        history.append("\n$name")
        history.appendLine(", $description")
        history.appendLine("---------------------")

        val iterator = menuComponents.iterator()
        while (iterator.hasNext()) {
            history.append(iterator.next().print())
        }
        return history.toString()
    }

    override fun createIterator(): Iterator<MenuComponent?> {
        if (!::iterator.isInitialized) {
            iterator = CompositeIterator(menuComponents.iterator())
        }
        return iterator
    }
}