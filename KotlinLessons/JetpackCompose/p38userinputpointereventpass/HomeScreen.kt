package ru.korobeynikov.p38userinputpointereventpass

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    //scroll A and B, click B
    val logs = remember { mutableStateListOf<String>() }
    var offsetY by remember { mutableIntStateOf(0) }
    Column {
        Box(
            modifier = Modifier
                .offset { IntOffset.Zero.copy(y = offsetY) }
                .size(300.dp)
                .background(Color.LightGray)
                .inputA { offsetY += it }
        ) {
            Text("A")
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
                    .align(Alignment.Center)
                    .inputB(logs)
            ) {
                Text("B")
            }
        }
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

fun Modifier.inputB(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            var event1: PointerEvent
            do {
                event1 = awaitPointerEvent()
            } while (event1.type != PointerEventType.Press || event1.changes.first().isConsumed)
            event1.changes.forEach { it.consume() }

            var event2: PointerEvent
            do {
                event2 = awaitPointerEvent()
                val finalEvent = awaitPointerEvent(PointerEventPass.Final)
                if (finalEvent.type == PointerEventType.Move && finalEvent.changes.first().isConsumed) {
                    return@awaitEachGesture
                }
            } while (event2.type != PointerEventType.Release || event2.changes.first().isConsumed)
            event2.changes.forEach { it.consume() }
            logs.add("B click")
        }
    }
}

fun Modifier.inputA(offsetY: (Int) -> Unit): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            val press = awaitFirstDown(requireUnconsumed = false)
            verticalDrag(press.id) {
                val y = (it.position.y - it.previousPosition.y).toInt()
                offsetY(y)
                it.consume()
            }
        }
    }
}

@Composable
fun HomeScreenLogs() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .inputAThreePassesConsumed(logs)
        ) {
            Text("A")
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
                    .align(Alignment.Center)
                    .inputB(logs)
            ) {
                Text("B")
            }
        }
        Text(text = logs.takeLast(12).joinToString("\n"), fontSize = 14.sp)
    }
}

fun Modifier.inputBThreePassesConsumed(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                var event = awaitPointerEvent(PointerEventPass.Initial)
                logs.add("B Initial ${event.info()}")
                event = awaitPointerEvent(PointerEventPass.Main)
                logs.add("B Main ${event.info()}")
                event.changes.first().consume()
                event = awaitPointerEvent(PointerEventPass.Final)
                logs.add("B Final ${event.info()}")
            }
        }
    }
}

fun Modifier.inputAThreePassesConsumed(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                var event = awaitPointerEvent(PointerEventPass.Initial)
                logs.add("A Initial ${event.info()}")
                event = awaitPointerEvent(PointerEventPass.Main)
                logs.add("A Main ${event.info()}")
                event = awaitPointerEvent(PointerEventPass.Final)
                logs.add("A Final ${event.info()}")
            }
        }
    }
}

fun PointerEvent.info(): String {
    return "$type ${changes.first().isConsumed}"
}

fun Modifier.inputBThreePasses(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                var event = awaitPointerEvent(PointerEventPass.Initial)
                logs.add("B Initial ${event.type}")
                event = awaitPointerEvent(PointerEventPass.Main)
                logs.add("B Main ${event.type}")
                event = awaitPointerEvent(PointerEventPass.Final)
                logs.add("B Final ${event.type}")
            }
        }
    }
}

fun Modifier.inputAThreePasses(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                var event = awaitPointerEvent(PointerEventPass.Initial)
                logs.add("A Initial ${event.type}")
                event = awaitPointerEvent(PointerEventPass.Main)
                logs.add("A Main ${event.type}")
                event = awaitPointerEvent(PointerEventPass.Final)
                logs.add("A Final ${event.type}")
            }
        }
    }
}

fun Modifier.inputBOnePass(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                logs.add("B ${event.type}")
            }
        }
    }
}

fun Modifier.inputAOnePass(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                logs.add("A ${event.type}")
            }
        }
    }
}