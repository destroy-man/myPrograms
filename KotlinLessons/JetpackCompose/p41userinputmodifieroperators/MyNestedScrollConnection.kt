package ru.korobeynikov.p41userinputmodifieroperators

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.korobeynikov.p41userinputmodifieroperators.Utils.Companion.TAG

class MyNestedScrollConnection(
    private val sizeState: MutableState<Float>,
    private val scope: CoroutineScope,
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return if ((sizeState.value < 600f && available.y > 0) || (sizeState.value > 100f && available.y < 0)) {
            sizeState.value += available.y
            available
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        Log.d(TAG, "onPostFling, consumed = $consumed, available = $available")
        if (sizeState.value in 100f..300f) {
            animateTo(100f)
        } else if (sizeState.value in 301f..600f) {
            animateTo(600f)
        }
        return super.onPostFling(consumed, available)
    }

    private fun animateTo(value: Float) {
        scope.launch {
            animate(initialValue = sizeState.value, targetValue = value) { value, velocity ->
                sizeState.value = value
            }
        }
    }
}