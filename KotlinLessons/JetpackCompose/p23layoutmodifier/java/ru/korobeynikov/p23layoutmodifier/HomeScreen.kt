package ru.korobeynikov.p23layoutmodifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout

@Composable
fun HomeScreen() = Box(modifier = Modifier.background(Color.LightGray)) {
    Text(text = "Some text and more", modifier = Modifier.myLayout())
}

fun Modifier.myLayout() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(10, 10)
    }
}