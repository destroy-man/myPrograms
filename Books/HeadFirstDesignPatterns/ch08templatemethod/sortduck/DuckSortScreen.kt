package ru.korobeynikov.ch08templatemethod.sortduck

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DuckSortScreen() {
    Column {
        val ducks = arrayOf(
            Duck("Daffy", 8),
            Duck("Dewey", 2),
            Duck("Howard", 7),
            Duck("Louie", 2),
            Duck("Donald", 10),
            Duck("Huey", 2)
        )

        Text("Before sorting:")
        Ducks(ducks)

        ducks.sort()

        Text("\nAfter sorting:")
        Ducks(ducks)
    }
}

@Composable
fun Ducks(ducks: Array<Duck>) {
    ducks.forEach { duck ->
        Text(duck.toString())
    }
}