package ru.korobeynikov.p24ownmodifieroperatorschain

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout

const val TAG = "myLogs"

@Composable
fun HomeScreen() = Box(modifier = Modifier.background(Color.LightGray)) {
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