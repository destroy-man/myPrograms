package ru.korobeynikov.p32animationanimatevalueasstate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    //animateIntAsState with ViewModel
    Column {
        val animatedCount = animateIntAsState(
            targetValue = homeViewModel.visitorCount.collectAsState().value,
            animationSpec = tween(1000),
            label = "Count"
        )
        Text(text = "Visitors: ${animatedCount.value}", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenAnimateIntAsState() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })

        val animatedCount = animateIntAsState(
            targetValue = if (checked) 500 else 100,
            animationSpec = tween(1000),
            label = "Count"
        )
        Text(text = "${animatedCount.value}", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenAnimatable() {
    Column {
        val scope = rememberCoroutineScope()
        val animatableCount = remember {
            Animatable(initialValue = 100, typeConverter = Int.VectorConverter)
        }
        var checked by remember { mutableStateOf(false) }

        Checkbox(checked = checked, onCheckedChange = {
            checked = it
            scope.launch {
                animatableCount.animateTo(
                    targetValue = if (checked) 500 else 100,
                    animationSpec = tween(1000)
                )
            }
        })

        Text(text = "${animatableCount.value}", fontSize = 30.sp)
    }
}