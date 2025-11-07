package ru.korobeynikov.p41userinputmodifieroperators

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import ru.korobeynikov.p41userinputmodifieroperators.Utils.Companion.TAG

class MyNestedScrollConnectionAvailableTimes : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        Log.d(TAG, "onPreScroll, available = $available")
        return available.times(0.33f)
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        Log.d(TAG, "onPostScroll, consumed = $consumed, available = $available")
        return super.onPostScroll(consumed, available, source)
    }
}