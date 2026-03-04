package ru.korobeynikov.viewmodelapplication

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.korobeynikov.viewmodelapplication.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ViewModelApplication",
    ) {
        App()
    }
}