package ru.korobeynikov.p19derivedstateofsnapshotflow

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import ru.korobeynikov.p19derivedstateofsnapshotflow.Utils.Companion.TAG
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val sliderPosition = mutableFloatStateOf(1f)

    val roundedPosition by derivedStateOf {
        sliderPosition.floatValue.roundToInt()
    }

    init {
        viewModelScope.launch {
            val flow = snapshotFlow { sliderPosition.floatValue }
                .filter { it > 3 }
                .sample(1000)
            flow.collect {
                Log.d(TAG, "track position $it")
            }
        }
    }
}