package ru.korobeynikov.p45tooling

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class NamePreviewParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Elise",
        "Frank",
        "Julia"
    )
}