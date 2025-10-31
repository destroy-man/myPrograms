package ru.korobeynikov.p36userinputawaitfuctions

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun HomeScreen() {
    //currentEvent
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    var index = 0
                    awaitEachGesture {
                        val firstDown = awaitFirstDown()
                        logs.add("${index++} ${currentEvent.type}")
                        drag(firstDown.id) {
                            logs.add("${index++} ${currentEvent.type}")
                        }
                        logs.add("${index++} ${currentEvent.type}")
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenAwaitEachGestureClick() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown()
                        logs.add("press")
                        val lastChange = waitForUpOrCancellation()
                        logs.add("click: ${lastChange != null}")
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenAwaitEachGesture() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val event = awaitPointerEvent()
                        logs.add("event = ${event.type}")
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenWaitForUpOrCancellation() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitFirstDown()
                            logs.add("press")
                            val lastChange = waitForUpOrCancellation()
                            logs.add("click: ${lastChange != null}")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
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
                    awaitPointerEventScope {
                        while (true) {
                            val press = awaitFirstDown()
                            drag(press.id) {
                                logs.add("drag ${it.position.x.toInt() - press.position.x.toInt()}")
                            }
                            logs.add("released")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenAwaitDragOrCancellation() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val press = awaitFirstDown()
                            var drag: PointerInputChange?
                            do {
                                drag = awaitDragOrCancellation(press.id)
                                logs.add("drag: ${drag?.position?.x}")
                            } while (drag != null)
                            logs.add("released")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenAwaitLongPressOrCancellation() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val firstDown = awaitFirstDown()
                            val longPress = awaitLongPressOrCancellation(firstDown.id)
                            logs.add("long press: ${longPress != null}")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenAwaitFirstDown() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitFirstDown()
                            logs.add("pressed")

                            var event2: PointerEvent
                            do {
                                event2 = awaitPointerEvent()
                            } while (event2.type != PointerEventType.Release)
                            logs.add("click")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenLongPress() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            var event1: PointerEvent?
                            do {
                                event1 = awaitPointerEvent()
                            } while (event1.type != PointerEventType.Press)
                            val event1Position = event1.changes.first().position
                            logs.add("pressed")

                            withTimeoutOrNull(2000) {
                                var event2: PointerEvent
                                var event2Position: Offset
                                do {
                                    event2 = awaitPointerEvent()
                                    event2Position = event2.changes.first().position
                                } while (
                                    event2.type != PointerEventType.Release &&
                                    !(abs(event2Position.x - event1Position.x) > 10
                                            || abs(event2Position.y - event1Position.y) > 10)
                                )
                                logs.add("released")
                                event1 = null
                            }

                            if (event1 != null) {
                                logs.add("long press")
                            }
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenSwipe() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            var event1: PointerEvent
                            do {
                                event1 = awaitPointerEvent()
                            } while (event1.type != PointerEventType.Press)
                            val pressPosition = event1.changes.first().position

                            var event2: PointerEvent
                            do {
                                event2 = awaitPointerEvent()
                            } while (event2.type != PointerEventType.Release)
                            val releasePosition = event2.changes.first().position

                            val distance = releasePosition.x - pressPosition.x
                            if (distance > 200) {
                                logs.add("swipe $distance")
                            }
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenClick() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            var event1: PointerEvent
                            do {
                                event1 = awaitPointerEvent()
                            } while (event1.type != PointerEventType.Press)
                            logs.add("press")

                            var event2: PointerEvent
                            do {
                                event2 = awaitPointerEvent()
                            } while (event2.type != PointerEventType.Release)
                            logs.add("click")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

@Composable
fun HomeScreenTwoAnyEvents() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        var index = 0
                        while (true) {
                            awaitPointerEvent()
                            awaitPointerEvent()
                            logs.add("${index++}. click")
                        }
                    }
                }
        )
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}