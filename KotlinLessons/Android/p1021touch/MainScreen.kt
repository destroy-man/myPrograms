package ru.korobeynikov.p1021touch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun MainScreen() {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val x = event.changes[0].position.x
                        val y = event.changes[0].position.y
                        when (event.type) {
                            PointerEventType.Press -> text = "Press: $x,$y"
                            PointerEventType.Move -> text = "Move: $x,$y"
                            PointerEventType.Release -> text = "Release: $x,$y"
                        }
                    }
                }
            }
    ) {
        Text(text)
    }
}