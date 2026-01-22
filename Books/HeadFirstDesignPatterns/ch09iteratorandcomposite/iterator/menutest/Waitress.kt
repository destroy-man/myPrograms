package ru.korobeynikov.ch09iteratorandcomposite.iterator.menutest

import ru.korobeynikov.ch09iteratorandcomposite.MenuItem
import ru.korobeynikov.ch09iteratorandcomposite.iterator.dinermenu.DinerMenu
import ru.korobeynikov.ch09iteratorandcomposite.iterator.Iterator
import ru.korobeynikov.ch09iteratorandcomposite.iterator.pancakehouse.PancakeHouseMenu

class Waitress(private val pancakeHouseMenu: PancakeHouseMenu, private val dinerMenu: DinerMenu) {

    fun printMenu(): String {
        val menu = StringBuilder()
        val pancakeIterator = pancakeHouseMenu.createIterator()
        val dinerIterator = dinerMenu.createIterator()
        menu.appendLine("MENU\n----\nBREAKFAST")
        menu.append(printMenu(pancakeIterator))
        menu.appendLine("\nLUNCH")
        menu.append(printMenu(dinerIterator))
        return menu.toString()
    }

    private fun printMenu(iterator: Iterator): String {
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