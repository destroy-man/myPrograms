package ru.korobeynikov.p26customlayouttimelineparentdata

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import ru.korobeynikov.p26customlayouttimelineparentdata.Position.PARALLEL
import ru.korobeynikov.p26customlayouttimelineparentdata.Position.END

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    //ParentData
    Timeline {
        Text("Task 1")
        Text("Task 2", modifier = Modifier.position(PARALLEL))
        Text("Task 3")
        Text("Task 4")
        Text("Task 5", modifier = Modifier.position(END))

        Text("Task 6")
        Text("Task 7", modifier = Modifier.position(PARALLEL))
        Text("Task 8", modifier = Modifier.position(PARALLEL))
        Text("Task 9")
        Text("Task 10")
    }
}

@Composable
fun Timeline(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
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
                    PARALLEL -> {}
                    END -> x = 0
                    null -> x += placeable.width
                }
                y += placeable.height
            }
        }
    }
}

fun Modifier.position(position: Position) = this.then(PositionParentData(position))

@Composable
fun HomeScreenLayoutIds() {
    TimelineLayoutIds {
        Text("Task 1")
        Text("Task 2", modifier = Modifier.layoutId("parallel"))
        Text("Task 3")
        Text("Task 4")
        Text("Task 5", modifier = Modifier.layoutId("end"))

        Text("Task 6")
        Text("Task 7", modifier = Modifier.layoutId("parallel"))
        Text("Task 8", modifier = Modifier.layoutId("parallel"))
        Text("Task 9")
        Text("Task 10")
    }
}

@Composable
fun TimelineLayoutIds(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = y)
                val layoutId = (placeable as? Measurable)?.layoutId
                when (layoutId) {
                    "parallel" -> {}
                    "end" -> x = 0
                    null -> x += placeable.width
                }
                y += placeable.height
            }
        }
    }
}

@Composable
fun HomeScreenSimpleLayoutIds() {
    TimelineSimpleLayoutIds {
        Text("Task 1", modifier = Modifier.layoutId("1"))
        Text("Task 2")
        Text("Task 3", modifier = Modifier.layoutId("3"))
        Text("Task 4")
    }
}

@Composable
fun TimelineSimpleLayoutIds(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            Log.d(TAG, "layoutId = ${measurable.layoutId}")
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = y)
                x += placeable.width
                y += placeable.height
            }
        }
    }
}

@Composable
fun HomeScreenDefault() {
    TimelineDefault {
        Text("Task 1")
        Text("Task 2")
        Text("Task 3")
        Text("Task 4")
    }
}

@Composable
fun TimelineDefault(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints) //Measure children
        }
        layout(constraints.maxWidth, constraints.maxHeight) { //Decide own size
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = y) //Place children
                x += placeable.width
                y += placeable.height
            }
        }
    }
}