package ru.korobeynikov.ch01introduction.animal

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AnimalScreen() {
    //вместо жесткой фиксации подтипа в коде (Dog()), объект конкретной реализации
    //присваивается во время выполнения:
    val a = getAnimal("dog")
    Text(text = a.makeSound())

    /*Программирование на уровне интерфейса/супертипа
    val animal: Animal = Dog()
    Text(text = animal.makeSound())
     */

    /*Программирование на уровне реализации
    val d = Dog()
    Text(text = d.bark())
     */
}

fun getAnimal(typeAnimal: String): Animal {
    return when (typeAnimal) {
        "dog" -> Dog()
        else -> Cat()
    }
}