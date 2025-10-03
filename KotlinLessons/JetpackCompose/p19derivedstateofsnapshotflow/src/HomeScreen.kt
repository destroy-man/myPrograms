package ru.korobeynikov.p19derivedstateofsnapshotflow

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import ru.korobeynikov.p19derivedstateofsnapshotflow.Utils.Companion.TAG
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    //derivedStateOf vs snapshotFlow.collectAsState
    Column {
        var count by remember { mutableIntStateOf(0) }
        Text(text = "count = $count", modifier = Modifier.clickable { count++ })

        val derivedState = remember {
            derivedStateOf {
                Log.d(TAG, "derivedState ${count * 10}")
            }
        }

        val snapshotState = remember {
            snapshotFlow {
                Log.d(TAG, "snapshotState ${count * 10}")
            }
        }.collectAsState(initial = 0)
    }
}

@Composable
fun HomeScreenSnapshotFlowVM(homeViewModel: HomeViewModel = hiltViewModel()) {
    Column {
        MySlider(homeViewModel.sliderPosition)
    }
}

@Composable
fun HomeScreenSnapshotFlow() {
    Column {
        val sliderPosition = remember { mutableFloatStateOf(1f) }
        MySlider(sliderPosition)
        TrackPosition(position = sliderPosition)
    }
}

@OptIn(FlowPreview::class)
@Composable
fun TrackPosition(position: FloatState) {
    LaunchedEffect(Unit) {
        val flow = snapshotFlow { position.floatValue }
            .filter { it > 3 }
            .sample(1000)
        flow.collect {
            Log.d(TAG, "track position $it")
        }
    }
}

@Composable
fun HomeScreenDerivedStateVM(homeViewModel: HomeViewModel = hiltViewModel()) {
    Column {
        MySlider(homeViewModel.sliderPosition)
        Text("${homeViewModel.roundedPosition}")
        Log.d(TAG, "HomeScreen ${homeViewModel.roundedPosition}")
    }
}

@Composable
fun HomeScreenSliderDerivedState() {
    Column {
        val sliderPosition = remember { mutableFloatStateOf(1f) }
        MySlider(sliderPosition)
        val roundedPosition by remember {
            derivedStateOf {
                sliderPosition.floatValue.roundToInt()
            }
        }
        Text("$roundedPosition")
        Log.d(TAG, "HomeScreen $roundedPosition")
    }
}

@Composable
fun MySlider(sliderPosition: MutableFloatState) {
    Slider(
        value = sliderPosition.floatValue,
        valueRange = 1f..10f,
        onValueChange = { sliderPosition.floatValue = it }
    )
}

@Composable
fun HomeScreenWithoutDerivedState() {
    Column {
        var count by remember { mutableIntStateOf(0) }
        Text(text = "count = $count", modifier = Modifier.clickable { count++ })

        Text(text = "countBinary = ${count.toString(2)}")
    }
}

@Composable
fun HomeScreenDerivedStateOf() {
    Column {
        var count by remember { mutableIntStateOf(0) }
        Text(text = "count = $count", modifier = Modifier.clickable { count++ })

        val countBinary by remember {
            derivedStateOf {
                count.toString(2)
            }
        }
        Text(text = "countBinary = $countBinary")
    }
}