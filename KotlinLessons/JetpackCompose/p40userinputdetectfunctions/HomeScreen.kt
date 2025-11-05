package ru.korobeynikov.p40userinputdetectfunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
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
    //Transform gestures with panZoomLock
    val point = remember { mutableStateOf(Offset.Zero) }
    val pointSize = remember { mutableFloatStateOf(100f) }
    val pointDegrees = remember { mutableFloatStateOf(0f) }
    val color = remember { mutableStateOf(Color.Green) }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectTransformGestures(panZoomLock = true) { centroid, pan, zoom, rotation ->
                        point.value = centroid
                        pointSize.floatValue *= zoom
                        pointDegrees.floatValue += rotation
                        color.value = when {
                            pan.y > 5 -> Color.Blue
                            pan.y < -5 -> Color.Red
                            else -> Color.Green
                        }
                    }
                }
                .drawWithContent {
                    drawContent()
                    rotate(degrees = pointDegrees.floatValue, pivot = point.value) {
                        drawPoints(
                            listOf(point.value),
                            PointMode.Points,
                            color.value,
                            pointSize.floatValue
                        )
                    }
                }
        )
    }
}

@Composable
fun HomeScreenTransformGestures() {
    val point = remember { mutableStateOf(Offset.Zero) }
    val pointSize = remember { mutableFloatStateOf(100f) }
    val pointDegrees = remember { mutableFloatStateOf(0f) }
    val color = remember { mutableStateOf(Color.Green) }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        point.value = centroid
                        pointSize.floatValue *= zoom
                        pointDegrees.floatValue += rotation
                        color.value = when {
                            pan.y > 5 -> Color.Blue
                            pan.y < -5 -> Color.Red
                            else -> Color.Green
                        }
                    }
                }
                .drawWithContent {
                    drawContent()
                    rotate(degrees = pointDegrees.floatValue, pivot = point.value) {
                        drawPoints(
                            listOf(point.value),
                            PointMode.Points,
                            color.value,
                            pointSize.floatValue
                        )
                    }
                }
        )
    }
}

@Composable
fun HomeScreenDrag() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { position -> logs.add("start $position") },
                        onDragEnd = { logs.add("end") },
                        onDrag = { _, dragAmount -> logs.add("drag $dragAmount") }
                    )
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenPress() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { position ->
                            logs.add("onPress $position")
                            val released = tryAwaitRelease()
                            logs.add("onPress released $released")
                        }
                    )
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenTapLongPress() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { position -> logs.add("onTab $position") },
                        onLongPress = { position -> logs.add("onLongPress $position") }
                    )
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenTapDoubleTapLongPress() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { position -> logs.add("onTab $position") },
                        onDoubleTap = { position -> logs.add("onDoubleTab $position") },
                        onLongPress = { position -> logs.add("onLongPress $position") }
                    )
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}