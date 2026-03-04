package ru.korobeynikov.viewmodelapplication

import androidx.compose.ui.window.ComposeUIViewController
import ru.korobeynikov.viewmodelapplication.presentation.App

fun MainViewController() = ComposeUIViewController { App() }