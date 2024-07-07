package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа со строками и регулярными выражениями
//Реrулярные выражения и строки в тройных кавычках
@Composable
fun Chapter3_5_2() {
    Text(text = " ${parsePath("/Users/yole/kotlin-book/chapter.adoc")}")
}

fun parsePath(path: String): String {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        return "Dir: $directory, name: $filename, ext: $extension"
    }
    return ""
}