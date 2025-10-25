package ru.korobeynikov.p30animationanimatable

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    val animatable = remember {
        Animatable(initialValue = 10.dp, typeConverter = Dp.VectorConverter)
    }

    Column {
        Row {
            MyTargetButton(animatable, 10.dp, 1000)
            Spacer(modifier = Modifier.width(16.dp))
            MyTargetButton(animatable, 300.dp, 5000)
        }

        Spacer(
            modifier = Modifier
                .background(color = Color.Green)
                .height(50.dp)
                .width(animatable.value)
        )
    }
}

@Composable
private fun MyTargetButton(
    animatable: Animatable<Dp, AnimationVector1D>,
    targetValue: Dp,
    duration: Int,
) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            val animationResult = animatable.animateTo(
                targetValue,
                tween(duration, easing = LinearEasing)
            ) {
                Log.d(TAG, "velocity = $velocity")
            }
            Log.d(TAG, "end reason = ${animationResult.endReason}")
        }
    }) {
        Text(text = targetValue.value.toInt().toString())
    }
}

@Composable
fun HomeScreenMyAnimation() {
    val myAnimation = remember {
        MyAnimation(initialValue = 10.dp)
    }

    Column {
        Row {
            MyTargetButtonMyAnimation(myAnimation, 10.dp, 1000)
            Spacer(modifier = Modifier.width(16.dp))
            MyTargetButtonMyAnimation(myAnimation, 300.dp, 5000)
        }
        Spacer(
            modifier = Modifier
                .background(color = Color.Green)
                .height(50.dp)
                .width(myAnimation.animationValueState.value)
        )
    }
}

@Composable
private fun MyTargetButtonMyAnimation(myAnimation: MyAnimation, targetValue: Dp, duration: Int) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            myAnimation.animateTo(
                targetValue,
                tween(duration, easing = LinearEasing)
            )
        }
    }) {
        Text(text = targetValue.value.toInt().toString())
    }
}

@Composable
fun HomeScreenAnimationBackAndForward() {
    val myAnimation = remember {
        MyAnimationFixSpec(
            initialValue = 10.dp,
            tween(5000, easing = LinearEasing)
        )
    }

    Column {
        Row {
            val scope = rememberCoroutineScope()
            Button(onClick = {
                scope.launch {
                    myAnimation.animateTo(10.dp)
                }
            }) {
                Text(text = "10")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                scope.launch {
                    myAnimation.animateTo(300.dp)
                }
            }) {
                Text(text = "300")
            }
        }
        Spacer(
            modifier = Modifier
                .background(color = Color.Green)
                .height(50.dp)
                .width(myAnimation.animationValueState.value)
        )
    }
}

@Composable
fun HomeScreenWithClassAnimation() {
    val myAnimation = remember {
        MyAnimationFixSpec(
            initialValue = 10.dp,
            tween(5000, easing = LinearEasing)
        )
    }

    Spacer(
        modifier = Modifier
            .background(color = Color.Green)
            .height(50.dp)
            .width(myAnimation.animationValueState.value)
    )

    LaunchedEffect(Unit) {
        myAnimation.animateToFixInitialValue(300.dp)
    }
}

@Composable
fun HomeScreenWithoutClassAnimation() {
    val animation = remember {
        TargetBasedAnimation(
            animationSpec = tween(5000, easing = LinearEasing),
            typeConverter = Dp.VectorConverter,
            initialValue = 10.dp,
            targetValue = 300.dp
        )
    }

    val animationValueState = remember { mutableStateOf(10.dp) }

    Spacer(
        modifier = Modifier
            .background(color = Color.Green)
            .height(50.dp)
            .width(animationValueState.value)
    )

    LaunchedEffect(key1 = Unit) {
        val startTime = withFrameNanos { it }
        var playTime = 0L
        while (playTime <= animation.durationNanos) {
            playTime = withFrameNanos { it } - startTime
            animationValueState.value = animation.getValueFromNanos(playTime)
        }
    }
}