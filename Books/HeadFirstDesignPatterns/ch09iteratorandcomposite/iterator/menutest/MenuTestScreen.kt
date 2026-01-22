package ru.korobeynikov.ch09iteratorandcomposite.iterator.menutest

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch09iteratorandcomposite.iterator.dinermenu.DinerMenu
import ru.korobeynikov.ch09iteratorandcomposite.iterator.pancakehouse.PancakeHouseMenu

@Composable
fun MenuTestScreen() {
    //Паттерн Итератор - кастомная реализация
    val pancakeHouseMenu = PancakeHouseMenu()
    val dinerMenu = DinerMenu()
    val waitress = Waitress(pancakeHouseMenu, dinerMenu)
    Text(waitress.printMenu())
}