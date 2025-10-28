package ru.korobeynikov.p31animationanimationspec

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
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
    //snap
    val myAnimation = remember {
        Animatable(initialValue = 0.dp, typeConverter = Dp.VectorConverter)
    }

    Spacer(
        modifier = Modifier
            .background(Color.Green)
            .height(50.dp)
            .width(myAnimation.value)
    )

    LaunchedEffect(Unit) {
        delay(1000)
        myAnimation.animateTo(targetValue = 250.dp, animationSpec = createSpec())
    }
}

private fun createSpec(): AnimationSpec<Dp> = snap()

private fun createSpecInfiniteRepeatable(): AnimationSpec<Dp> = infiniteRepeatable(
    animation = tween(durationMillis = 1000),
    repeatMode = RepeatMode.Reverse
)

private fun createSpecRepeatableRestart(): AnimationSpec<Dp> = repeatable(
    iterations = 3,
    animation = tween(durationMillis = 1000),
    repeatMode = RepeatMode.Restart
)

private fun createSpecRepeatableReverse(): AnimationSpec<Dp> = repeatable(
    iterations = 3,
    animation = tween(durationMillis = 1000),
    repeatMode = RepeatMode.Reverse
)

private fun createSpecStepForwardAndStepBack(): AnimationSpec<Dp> = keyframes {
    durationMillis = 5000
    for (i in 1..5) {
        (20.dp + 50.dp * i) at (1000 * i - 200)
        (50.dp * i) at 1000 * i
    }
}

private fun createSpecKeyframesForwardAndBack(): AnimationSpec<Dp> = keyframes {
    durationMillis = 5000
    200.dp at 3000
    150.dp at 4000
}

private fun createSpecKeyframesStepEasingInBegin(): AnimationSpec<Dp> = keyframes {
    durationMillis = 5000
    0.dp at 0 using StepEasing
    50.dp at 2000
    200.dp at 3000
}

private fun createSpecKeyframesUsingStepEasing(): AnimationSpec<Dp> = keyframes {
    durationMillis = 5000
    50.dp at 2000 using StepEasing
    200.dp at 3000
}

private fun createSpecKeyframesFewPoints(): AnimationSpec<Dp> = keyframes {
    durationMillis = 5000
    50.dp atFraction 0.4f
    200.dp atFraction 0.6f
}

private fun createSpecKeyframesSinglePoint(): AnimationSpec<Dp> = keyframes {
    durationMillis = 1000
    200.dp at 300
}

private fun createSpecSpringVisibilityThreshold30dp(): AnimationSpec<Dp> =
    spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessVeryLow,
        visibilityThreshold = 30.dp
    )

private fun createSpecSpringVisibilityThreshold1dp(): AnimationSpec<Dp> =
    spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessVeryLow,
        visibilityThreshold = 1.dp
    )

private fun createSpecSpringStiffnessHigh(): AnimationSpec<Dp> =
    spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessHigh)

private fun createSpecSpringDampingRatioHighBouncy(): AnimationSpec<Dp> =
    spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow)

private fun createSpecSpringDampingRatioLowBouncy(): AnimationSpec<Dp> =
    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)

private fun createSpecTweenWithDelayMillis(): AnimationSpec<Dp> =
    tween(delayMillis = 2000, durationMillis = 1000, easing = LinearEasing)

private fun createSpecTweenStepEasing(): AnimationSpec<Dp> =
    tween(durationMillis = 1000, easing = StepEasing)

val StepEasing: Easing = Easing { fraction ->
    when {
        fraction < 0.25 -> 0f
        fraction < 0.5f -> 0.25f
        fraction < 0.75f -> 0.5f
        fraction < 1f -> 0.75f
        else -> 1f
    }
}

private fun createSpecTweenLinearEasing(): AnimationSpec<Dp> =
    tween(durationMillis = 1000, easing = LinearEasing)