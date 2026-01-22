package ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.menutest

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.cafemenu.CafeMenu
import ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.dinermenu.DinerMenu
import ru.korobeynikov.ch09iteratorandcomposite.kotliniterator.pancakehouse.PancakeHouseMenu

@Composable
fun MenuTestScreen() {
    //Использование котлиновского итератора
    val pancakeHouseMenu = PancakeHouseMenu()
    val dinerMenu = DinerMenu()
    val cafeMenu = CafeMenu()
    val waitress = Waitress(pancakeHouseMenu, dinerMenu, cafeMenu)
    Text(waitress.printMenu())
}