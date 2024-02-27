package ru.korobeynikov.p29animationbasics

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    val animation = remember {
        TargetBasedAnimation(
            animationSpec = tween(5000, easing = LinearEasing),
            typeConverter = Int.VectorConverter,
            initialValue = 0,
            targetValue = 100
        )
    }
    val animationValueState = remember {
        mutableIntStateOf(0)
    }
    Text(text = "value = ${animationValueState.intValue}", fontSize = 30.sp)
    LaunchedEffect(key1 = Unit) {
        val startTime = withFrameNanos { it }
        var playTime = 0L
        while (playTime <= animation.durationNanos) {
            playTime = withFrameNanos { it } - startTime
            animationValueState.intValue = animation.getValueFromNanos(playTime)
        }
    }
}