package ru.korobeynikov.chapter11

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Гибкое вложение блоков с использованием соглашения "invoke"
//Соглашение "invoke" и типы функций
@Composable
fun Chapter11_3_2() {
    val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
    val i2 = Issue(
        "KT-12183",
        "Kotlin",
        "Feature",
        "Normal",
        "Intention: convert several calls on same receiver to with/apply"
    )
    val predicate = ImportantIssuesPredicate("IDEA")
    Column {
        for (issue in listOf(i1, i2).filter(predicate))
            Text(text = " ${issue.id}")
    }
}