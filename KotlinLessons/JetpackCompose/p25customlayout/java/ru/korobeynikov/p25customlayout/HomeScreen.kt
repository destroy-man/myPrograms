package ru.korobeynikov.p25customlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() = MyColumn(modifier = Modifier
    .height(150.dp)
    .background(Color.Gray)) {
    Text(text = "Text 1")
    Text(text = "Text 2")
    Text(text = "Text 3")
    Text(text = "Text 4")
}

@Composable
fun MyColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }
        layout(
            constraints.constrainWidth(placeables.maxOf { it.width }),
            constraints.constrainHeight(placeables.sumOf { it.height })
        ) {
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = y)
                y += placeable.height
            }
        }
    }