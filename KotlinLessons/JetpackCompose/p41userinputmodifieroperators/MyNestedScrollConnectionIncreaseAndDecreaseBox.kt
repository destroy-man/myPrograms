package ru.korobeynikov.p41userinputmodifieroperators

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

class MyNestedScrollConnectionIncreaseAndDecreaseBox(private val sizeState: MutableState<Float>) :
    NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return if ((sizeState.value < 600f && available.y > 0) || (sizeState.value > 100f && available.y < 0)) {
            sizeState.value += available.y
            available
        } else {
            Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset {
        return available
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        return available
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return super.onPostFling(consumed, available)
    }
}