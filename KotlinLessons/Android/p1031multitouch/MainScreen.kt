package ru.korobeynikov.p1031multitouch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun MainScreen() {
    val touches = remember { mutableStateListOf<String>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        event.changes.forEach { change ->
                            val id = change.id.value
                            val x = change.position.x
                            val y = change.position.y
                            when (event.type) {
                                PointerEventType.Press -> touches.add("Press [$id]: $x,$y")
                                PointerEventType.Move -> touches.add("Move [$id]: $x,$y")
                                PointerEventType.Release -> touches.add("Release [$id]: $x,$y")
                            }
                        }
                    }
                }
            }
    ) {
        Text(touches.takeLast(15).joinToString("\n"))
    }
}