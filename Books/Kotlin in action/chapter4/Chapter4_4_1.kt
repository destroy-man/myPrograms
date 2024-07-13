package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_4.CaseInsensitiveFileComparator
import ru.korobeynikov.chapter4.chapter4_4.Person
import java.io.File

//Ключевое слово object: совместное объявление класса и его экземпляра
//Объявление объекта : простая реализация шаблона "Одиночка"
@Composable
fun Chapter4_4_1() {
    val files = listOf(File("/Z"), File("/a"))
    val persons = listOf(Person("Bob"), Person("Alice"))
    Column {
        Text(text = " ${CaseInsensitiveFileComparator.compare(File("/User"), File("/user"))}")
        Text(text = " ${files.sortedWith(CaseInsensitiveFileComparator)}")
        Text(text = " ${persons.sortedWith(Person.NameComparator)}")
    }
}