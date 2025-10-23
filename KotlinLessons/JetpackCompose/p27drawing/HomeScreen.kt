package ru.korobeynikov.p27drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    //Draw vector
    val vector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
    val painter = rememberVectorPainter(vector)
    Canvas(modifier = Modifier.fillMaxSize()) {
        with(painter) {
            draw(painter.intrinsicSize, colorFilter = ColorFilter.tint(color = Color.Green))
        }
    }
}

@Composable
fun HomeScreenDrawBitmap() {
    val bitmap = ImageBitmap.imageResource(id = R.drawable.box)
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawImage(bitmap)
    }
}

fun getBitmapDrawableFromVectorDrawable(context: Context, vectorDrawableId: Int): BitmapDrawable? {
    val drawable = ContextCompat.getDrawable(context, vectorDrawableId)
    return when (drawable) {
        is VectorDrawable -> {
            val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap.toDrawable(context.resources)
        }

        is BitmapDrawable -> {
            drawable
        }

        else -> {
            null
        }
    }
}

@Composable
fun HomeScreenDrawText() {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = Modifier.fillMaxSize()) {
        val measuredText = textMeasurer.measure(
            text = AnnotatedString(
                "Some text some text some text some text some text " +
                        "some text some text"
            ),
            constraints = Constraints.fixedWidth(size.width.toInt() / 2),
            style = TextStyle(fontSize = 20.sp)
        )
        drawText(measuredText)
    }
}

@Composable
fun HomeScreenWithTransform() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        withTransform({
            translate(-center.x + 100f, -center.y + 100f)
            rotate(degrees = 45f)
        }) {
            cross()
        }
    }
}

@Composable
fun HomeScreenTranslateRotateLeft() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(-center.x + 100f, -center.y + 100f) {
            rotate(degrees = 45f) {
                cross()
            }
        }
    }
}

@Composable
fun HomeScreenTranslateRotateCenter() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(100f, 100f) {
            rotate(degrees = 45f) {
                cross()
            }
        }
    }
}

@Composable
fun HomeScreenCenterCrossRotate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(degrees = 45f) {
            cross()
        }
    }
}

@Composable
fun HomeScreenCenterCross() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        cross()
    }
}

private fun DrawScope.cross() {
    drawLine(
        color = Color.Green,
        start = center.plus(Offset(-50f, -50f)),
        end = center.plus(Offset(50f, 50f)),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Green,
        start = center.plus(Offset(50f, -50f)),
        end = center.plus(Offset(-50f, 50f)),
        strokeWidth = 5f
    )
}

@Composable
fun HomeScreenInsetHalfWidth() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        inset(100f, 100f) {
            drawRect(color = Color.Green, size = Size(size.width / 2, size.width / 2))
        }
    }
}

@Composable
fun HomeScreenTranslateHalfWidth() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        Log.d(TAG, "size = $size")
        translate(100f, 100f) {
            drawRect(color = Color.Green, size = Size(size.width / 2, size.width / 2))
        }
    }
}

@Composable
fun HomeScreenRectTranslate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(100f, 100f) {
            drawRect(color = Color.Green, size = Size(200f, 200f))
        }
    }
}

@Composable
fun HomeScreenRectInset() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        inset(100f, 100f) {
            drawRect(color = Color.Green, size = Size(200f, 200f))
        }
    }
}

@Composable
fun HomeScreenCrossTranslate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 100f, top = 100f) {
            leftTopCross()
        }
    }
}

@Composable
fun HomeScreenCrossRotate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(degrees = 45f, pivot = Offset(50f, 50f)) {
            leftTopCross()
        }
    }
}

@Composable
fun HomeScreenCrossScale() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        scale(scaleX = 2f, scaleY = 2f, pivot = Offset(0f, 0f)) {
            leftTopCross()
        }
    }
}

@Composable
fun HomeScreenCrossDefault() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        leftTopCross()
    }
}

private fun DrawScope.leftTopCross() {
    drawLine(
        color = Color.Green,
        start = Offset(0f, 0f),
        end = Offset(100f, 100f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Green,
        start = Offset(100f, 0f),
        end = Offset(0f, 100f),
        strokeWidth = 5f
    )
}

@Composable
fun HomeScreenOffsetRect() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            color = Color.Green,
            size = Size(200f, 300f),
            topLeft = Offset(50f, 150f)
        )
    }
}

@Composable
fun HomeScreenSizeInPx() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = Color.Green, size = Size(200.dp.toPx(), 300.dp.toPx()))
    }
}

@Composable
fun HomeScreenSizeInDp() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = Color.Green, size = Size(200f, 300f))
    }
}