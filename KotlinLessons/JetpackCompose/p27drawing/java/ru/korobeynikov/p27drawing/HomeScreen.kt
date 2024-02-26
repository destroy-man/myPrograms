package ru.korobeynikov.p27drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource

@Composable
fun HomeScreen() {
    val vector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
    val painter = rememberVectorPainter(image = vector)
    Canvas(modifier = Modifier.fillMaxSize()) {
        with(painter) {
            draw(painter.intrinsicSize, colorFilter = ColorFilter.tint(color = Color.Green))
        }
    }
}