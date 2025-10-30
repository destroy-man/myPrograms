package ru.korobeynikov.p35pointereventpointerinputchange

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    //Calculate centroid, zoom and rotation
    val point = remember { mutableStateOf(Offset.Zero) }
    val pointSize = remember { mutableFloatStateOf(100f) }
    val pointDegrees = remember { mutableFloatStateOf(0f) }
    Column {
        Box(modifier = Modifier
            .size(300.dp)
            .background(Color.LightGray)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val centroid = event.calculateCentroid()
                        point.value = centroid.takeIf { it != Offset.Unspecified } ?: Offset.Zero
                        val zoom = event.calculateZoom()
                        pointSize.floatValue *= zoom
                        val rotation = event.calculateRotation()
                        pointDegrees.floatValue += rotation
                    }
                }
            }
            .drawWithContent {
                drawContent()
                rotate(pointDegrees.floatValue, pivot = point.value) {
                    drawPoints(
                        listOf(point.value), PointMode.Points, Color.Green, pointSize.floatValue
                    )
                }
            }
        )
    }
}

@Composable
fun HomeScreenCalculateCentroid() {
    val point = remember { mutableStateOf(Offset.Zero) }
    Column {
        Box(modifier = Modifier
            .size(300.dp)
            .background(Color.LightGray)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val centroid = event.calculateCentroid()
                        point.value = centroid.takeIf { it != Offset.Unspecified } ?: Offset.Zero
                    }
                }
            }
            .drawWithContent {
                drawContent()
                drawPoints(listOf(point.value), PointMode.Points, Color.Green, 50f)
            }
        )
    }
}

@Composable
fun HomeScreenMultitouchEvents() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val changes = event.changes.map { "${it.id.value} ${it.pressed}" }
                            logs.add("${event.type} $changes")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(15).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenChangesFirstElement() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes[0]
                            logs.add("${event.type} ${change.position} ${change.pressed} ")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(15).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenManyEvents() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            logs.add("event ${event.type}")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(15).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenSingleEvent() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        val event = awaitPointerEvent()
                        logs.add("event ${event.type}")
                    }
                }
        )
        Text(text = logs.takeLast(15).joinToString("\n"), fontSize = 14.sp)
    }
}