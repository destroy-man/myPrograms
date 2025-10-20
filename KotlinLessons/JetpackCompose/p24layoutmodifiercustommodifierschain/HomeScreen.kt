package ru.korobeynikov.p24layoutmodifiercustommodifierschain

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    //Custom padding
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        Text(
            text = "Some text",
            modifier = Modifier
                .background(Color.Yellow)
                .myPadding(1)
                .background(Color.Red)
                .myPadding(2)
                .background(Color.Blue)
                .myPadding(3)
                .background(Color.Green)
        )
    }
}

fun Modifier.myPadding(id: Int) = layout { measurable, constraints ->
    Log.d(TAG, "myPadding $id, measure child")
    val placeable = measurable.measure(constraints)

    val myWidth = placeable.width + 50
    val myHeight = placeable.height + 50
    Log.d(
        TAG,
        "myPadding $id, child: (${placeable.width}, ${placeable.height}), me: ($myWidth, $myHeight)"
    )

    layout(myWidth, myHeight) {
        placeable.placeRelative(25, 25)
    }
}

@Composable
fun HomeScreenDefaultPadding() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        Text(
            text = "Some text",
            modifier = Modifier
                .background(Color.Yellow)
                .padding(25.px)
                .background(Color.Red)
                .padding(25.px)
                .background(Color.Blue)
                .padding(25.px)
                .background(Color.Green)
        )
    }
}

@Composable
fun HomeScreenMyPaddingStart() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        Text(text = "Some text", modifier = Modifier.myPaddingStart(20.dp))
    }
}

fun Modifier.myPaddingStart(padding: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width + padding.roundToPx(), placeable.height) {
        placeable.placeRelative(padding.roundToPx(), 0)
    }
}

@Composable
fun HomeScreenMyOffsetX() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        Text(text = "Some text", modifier = Modifier.myOffsetX(20.dp))
    }
}

fun Modifier.myOffsetX(offset: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(offset.roundToPx(), 0)
    }
}

@Composable
fun HomeScreenMyWidth() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        Text(text = "Some text", modifier = Modifier.myWidth(200.dp))
    }
}

fun Modifier.myWidth(width: Dp) = layout { measurable, constraints ->
    val myConstraints = constraints.copy(minWidth = width.roundToPx(), maxWidth = width.roundToPx())
    val placeable = measurable.measure(myConstraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

inline val Int.px: Dp
    @Composable get() = with(LocalDensity.current) {
        this@px.toDp()
    }