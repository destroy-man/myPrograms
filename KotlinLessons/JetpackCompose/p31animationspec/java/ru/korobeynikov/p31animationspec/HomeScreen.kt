package ru.korobeynikov.p31animationspec

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    val myAnimation = remember {
        Animatable(initialValue = 0.dp, typeConverter = Dp.VectorConverter)
    }
    Spacer(
        modifier = Modifier
            .background(Color.Green)
            .height(50.dp)
            .width(myAnimation.value)
    )
    LaunchedEffect(key1 = Unit) {
        delay(1000)
        myAnimation.animateTo(targetValue = 250.dp, animationSpec = createSpec())
    }
}

private fun createSpec(): AnimationSpec<Dp> = infiniteRepeatable(
    animation = tween(durationMillis = 1000),
    repeatMode = RepeatMode.Reverse
)