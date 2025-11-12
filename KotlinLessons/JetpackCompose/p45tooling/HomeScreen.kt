package ru.korobeynikov.p45tooling

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

@Composable
fun HomeScreen(name: String) {
    //LocalInspectionMode
    if (LocalInspectionMode.current) {
        Text(text = "Hello preview $name!")
    } else {
        Text(text = "Hello $name!")
    }
}

@Preview
@Composable
fun SinglePreview() {
    HomeScreen("Frank")
}

@ColorPreview
@Composable
fun MultiPreview() {
    HomeScreen("Frank")
}

@Preview
@Composable
fun PreviewWithParameter(
    @PreviewParameter(NamePreviewParameterProvider::class) name: String,
) {
    HomeScreen(name = name)
}