package ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.menutest

import ru.korobeynikov.ch09iteratorandcomposite.MenuItem

class Waitress(
    private val pancakeHouseMenu: Menu,
    private val dinerMenu: Menu,
    private val cafeMenu: Menu
) {

    fun printMenu(): String {
        val menu = StringBuilder()
        val pancakeIterator = pancakeHouseMenu.createIterator()
        val dinerIterator = dinerMenu.createIterator()
        val cafeIterator = cafeMenu.createIterator()
        menu.appendLine("MENU\n----\nBREAKFAST")
        menu.append(printMenu(pancakeIterator))
        menu.appendLine("\nLUNCH")
        menu.append(printMenu(dinerIterator))
        menu.appendLine("\nDINNER")
        menu.append(printMenu(cafeIterator))
        return menu.toString()
    }

    private fun printMenu(iterator: Iterator<MenuItem?>): String {
        val menu = StringBuilder()
        while (iterator.hasNext()) {
            val menuItem = iterator.next() as MenuItem
            menu.append("${menuItem.name}, ")
            menu.append("${menuItem.price} -- ")
            menu.appendLine(menuItem.description)
        }
        return menu.toString()
    }
}