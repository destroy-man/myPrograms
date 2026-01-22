package ru.korobeynikov.ch09iteratorandcomposite.composite.menu

abstract class MenuComponent(
    open val name: String,
    open val description: String,
    open val vegetarian: Boolean = false,
    open val price: Double = 0.0
) {

    open fun add(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }

    open fun remove(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }

    open fun getChild(i: Int): MenuComponent {
        throw UnsupportedOperationException()
    }

    open fun print(): String {
        throw UnsupportedOperationException()
    }

    abstract fun createIterator(): Iterator<MenuComponent?>
}