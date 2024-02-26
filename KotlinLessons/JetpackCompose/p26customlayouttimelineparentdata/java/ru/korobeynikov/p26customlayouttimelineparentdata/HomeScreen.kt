package ru.korobeynikov.p26customlayouttimelineparentdata

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable

@Composable
fun HomeScreen() = Timeline {
    Text(text = "Task 1")
    Text(text = "Task 2", modifier = Modifier.position(Position.PARALLEL))
    Text(text = "Task 3")
    Text(text = "Task 4")
    Text(text = "Task 5", modifier = Modifier.position(Position.END))

    Text(text = "Task 6")
    Text(text = "Task 7", modifier = Modifier.position(Position.PARALLEL))
    Text(text = "Task 8", modifier = Modifier.position(Position.PARALLEL))
    Text(text = "Task 9")
    Text(text = "Task 10")
}

@Composable
fun Timeline(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                val position =
                    ((placeable as? Measurable)?.parentData as? PositionParentData)?.position
                placeable.placeRelative(x = x, y = y)
                when (position) {
                    Position.PARALLEL -> {}
                    Position.END -> x = 0
                    null -> x += placeable.width
                }
                y += placeable.height
            }
        }
    }

fun Modifier.position(position: Position) = this.then(PositionParentData(position))