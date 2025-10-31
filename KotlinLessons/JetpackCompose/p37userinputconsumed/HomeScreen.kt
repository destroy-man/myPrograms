package ru.korobeynikov.p37userinputconsumed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    val logs = remember { mutableStateListOf<String>() }
    Column {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .inputA(logs)
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
    return this.myClickable { logs.add("B click") }
}

fun Modifier.inputA(logs: SnapshotStateList<String>): Modifier {
    return this.myClickable { logs.add("A click") }
}

fun Modifier.myClickable(onClick: () -> Unit): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            val firstDown = awaitFirstDown()
            firstDown.consume()
            val lastUp = waitForUpOrCancellation()
            lastUp?.consume()
            if (lastUp != null) onClick()
        }
    }
}

fun Modifier.myClickableLowLevelRealization(onClick: () -> Unit): Modifier {
    return this.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                var event1: PointerEvent
                do {
                    event1 = awaitPointerEvent()
                } while (event1.type != PointerEventType.Press || event1.changes.first().isConsumed)
                event1.changes.first().consume()

                var event2: PointerEvent
                do {
                    event2 = awaitPointerEvent()
                } while (event2.type != PointerEventType.Release || event2.changes.first().isConsumed)
                event2.changes.first().consume()

                onClick()
            }
        }
    }
}

fun Modifier.inputBConsume(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                var event1: PointerEvent
                do {
                    event1 = awaitPointerEvent()
                } while (event1.type != PointerEventType.Press)
                logs.add("B press")
                event1.changes.first().consume()

                var event2: PointerEvent
                do {
                    event2 = awaitPointerEvent()
                } while (event2.type != PointerEventType.Release)
                logs.add("B click")
                event2.changes.first().consume()
            }
        }
    }
}

fun Modifier.inputAIsConsumed(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                var event1: PointerEvent
                do {
                    event1 = awaitPointerEvent()
                } while (event1.type != PointerEventType.Press || event1.changes.first().isConsumed)
                logs.add("A press")

                var event2: PointerEvent
                do {
                    event2 = awaitPointerEvent()
                } while (event2.type != PointerEventType.Release || event2.changes.first().isConsumed)
                logs.add("A click")
            }
        }
    }
}

fun Modifier.inputBClickableAndAwaitEachGesture(logs: SnapshotStateList<String>): Modifier {
    return this.clickable { logs.add("B click") }
}

fun Modifier.inputAClickableAndAwaitEachGesture(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                logs.add("A ${event.type} consumed = ${event.changes.firstOrNull()?.isConsumed}")
            }
        }
    }
}

fun Modifier.inputBClickable(logs: SnapshotStateList<String>): Modifier {
    return this.clickable { logs.add("B click") }
}

fun Modifier.inputAClickable(logs: SnapshotStateList<String>): Modifier {
    return this.clickable { logs.add("A click") }
}

fun Modifier.inputBAnyEvent(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                logs.add("B ${event.type}")
            }
        }
    }
}

fun Modifier.inputAAnyEvent(logs: SnapshotStateList<String>): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                logs.add("A ${event.type}")
            }
        }
    }
}