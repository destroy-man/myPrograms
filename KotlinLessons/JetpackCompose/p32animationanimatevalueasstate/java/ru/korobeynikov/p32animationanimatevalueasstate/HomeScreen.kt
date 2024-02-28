package ru.korobeynikov.p32animationanimatevalueasstate

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() = Column {
    var checked by remember {
        mutableStateOf(false)
    }
    Checkbox(checked = checked, onCheckedChange = { checked = it })
    val animatableCount = animateIntAsState(
        targetValue = if (checked) 500 else 100,
        animationSpec = tween(1000),
        label = "Count"
    )
    Text(text = "${animatableCount.value}", fontSize = 30.sp)
}