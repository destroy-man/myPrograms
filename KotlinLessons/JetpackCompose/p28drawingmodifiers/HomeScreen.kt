package ru.korobeynikov.p28drawingmodifiers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    //Blur radius for text
    Text(
        text = "Some text",
        style = TextStyle(
            fontSize = 30.sp,
            shadow = Shadow(blurRadius = 3f)
        )
    )
}

@Composable
fun HomeScreenGraphicsLayer() {
    val degrees by produceState(0f) {
        while (true) {
            delay(100)
            value = (value + 5f).rem(360)
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { rotationZ = degrees }
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(listOf(Color.Green, Color.Blue)),
                    size = size.div(2f),
                    topLeft = Offset(size.width / 4f, size.height / 4f)
                )
            })
}

@Composable
fun HomeScreenWithoutGraphicsLayer() {
    val degrees by produceState(0f) {
        while (true) {
            delay(100)
            value = (value + 5f).rem(360)
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                rotate(degrees = degrees) {
                    drawRect(
                        brush = Brush.linearGradient(listOf(Color.Green, Color.Blue)),
                        size = size.div(2f),
                        topLeft = Offset(size.width / 4f, size.height / 4f)
                    )
                }
            })
}

@Composable
fun HomeScreenDrawWithCache() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                val calculatedSize = size / 2f
                onDrawBehind {
                    drawRect(color = Color.Green, size = calculatedSize)
                }
            })
}

@Composable
fun HomeScreenWithoutDrawWithCache() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val calculatedSize = size.div(2f)
                drawRect(color = Color.Green, size = calculatedSize)
            })
}

@Composable
fun HomeScreenDrawWithContent() {
    Text(text = "Some text", fontSize = 30.sp, modifier = Modifier.drawWithContent {
        drawRect(color = Color.Green, size = size)
        drawContent()
        drawLine(
            color = Color.Red,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 8f
        )
    })
}

@Composable
fun HomeScreenDrawBehindLine() {
    Text(text = "Some text", fontSize = 30.sp, modifier = Modifier.drawBehind {
        drawLine(
            color = Color.Red,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 8f
        )
    })
}

@Composable
fun HomeScreenDrawBehindText() {
    Text(text = "Some text", fontSize = 30.sp, modifier = Modifier.drawBehind {
        drawRect(color = Color.Green, size = size)
    })
}

@Composable
fun HomeScreenDrawBehindRect() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = Color.Green, size = Size(200f, 200f))
            })
}

@Composable
fun HomeScreenDrawRect() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = Color.Green, size = Size(200f, 200f))
    }
}