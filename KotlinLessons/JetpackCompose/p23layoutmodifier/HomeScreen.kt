package ru.korobeynikov.p23layoutmodifier

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.background(Color.LightGray)) {
        Text(text = "Some text", modifier = Modifier.myLayout())
    }
}

@Composable
fun HomeScreenBigSizeWidth() {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .width(500.px)) {
        Text(text = "Some text and more", fontSize = 20.sp, modifier = Modifier.myLayout())
    }
}

@Composable
fun HomeScreenBoxHeightPx() {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(600.px)) {
        Text(text = "Some text", modifier = Modifier.myLayout())
    }
}

@Composable
fun HomeScreenBoxHeightDp() {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .height(200.dp)) {
        Text(text = "Some text", modifier = Modifier.myLayout())
    }
}

fun Modifier.myLayout() = layout { measurable, constraints ->
    //increase placeRelative
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(10, 10)
    }
}

fun Modifier.myLayoutIncreasePlaceable() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width + 100, placeable.height + 100) {
        placeable.placeRelative(0, 0)
    }
}

fun Modifier.myLayoutLogPlaceable() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    Log.d(TAG, "placeable, width = ${placeable.width}, height = ${placeable.height}")
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

fun Modifier.myLayoutLogConstraints() = layout { measurable, constraints ->
    Log.d(TAG, "constraints = $constraints")
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

fun Modifier.myLayoutDefault() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints) //Measure children
    layout(placeable.width, placeable.height) { //Decide own size
        placeable.placeRelative(0, 0) //Place children
    }
}

inline val Int.px: Dp
    @Composable get() = with(LocalDensity.current) {
        this@px.toDp()
    }