package ru.korobeynikov.ch09iteratorandcomposite.composite

import ru.korobeynikov.ch09iteratorandcomposite.composite.menu.MenuComponent

class Waitress(private val allMenus: MenuComponent) {

    fun printMenu(): String {
        return allMenus.print()
    }

    fun printVegetarianMenu(): String {
        val menu = StringBuilder()
        val iterator = allMenus.createIterator()
        menu.appendLine("\nVEGETARIAN MENU\n----")
        while (iterator.hasNext()) {
            val menuComponent = iterator.next()
            if (menuComponent != null && menuComponent.vegetarian) {
                menu.append(menuComponent.print())
            }
        }
        return menu.toString()
    }
}