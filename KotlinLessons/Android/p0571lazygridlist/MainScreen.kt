package ru.korobeynikov.p0571lazygridlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    //Adaptive равномерно распределяет элементы по всей ширине
    val data = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
    LazyVerticalGrid(
        GridCells.Adaptive(80.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(data) { text ->
            ListElement(text)
        }
    }
}

@Composable
fun FixedSizeLazyGrid() {
    val data = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
    LazyVerticalGrid(GridCells.FixedSize(80.dp)) {
        items(data) { text ->
            ListElement(text)
        }
    }
}

@Composable
fun FixedLazyGrid() {
    val data = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(data) { text ->
            ListElement(text)
        }
    }
}

@Composable
fun ListElement(text: String) {
    Text(
        text,
        modifier = Modifier
            .height(40.dp)
            .background(Color(0x99000099)),
        fontSize = 20.sp
    )
}