package ru.korobeynikov.p30animationanimatable

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.unit.Dp

class MyAnimation(private val initialValue: Dp) {

    private val mutatorMutex= MutatorMutex()

    val animationValueState = mutableStateOf(initialValue)

    suspend fun animateTo(targetValue: Dp, animationSpec: AnimationSpec<Dp>){
        val animation = TargetBasedAnimation(
            animationSpec = animationSpec,
            typeConverter = Dp.VectorConverter,
            initialValue = animationValueState.value,
            targetValue = targetValue
        )

        mutatorMutex.mutate {
            val startTime= withFrameNanos { it }
            var playTime=0L
            while (playTime<=animation.durationNanos){
                playTime=withFrameNanos { it }-startTime
                animationValueState.value=animation.getValueFromNanos(playTime)
            }
        }
    }
}