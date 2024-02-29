package ru.korobeynikov.p33animationtransition

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) = Column {
    val transition = updateTransition(
        targetState = homeViewModel.visitorCount.collectAsState().value,
        label = "Visitors"
    )
    val animatedCount = transition.animateInt(
        transitionSpec = {
            tween(1000)
        },
        label = "Count"
    ) { state -> state }
    val animatedWidth = transition.animateDp(
        transitionSpec = {
            tween(1000)
        },
        label = "Width"
    ) { state ->
        (state / 500f * 300f).dp
    }
    val animatedColor = transition.animateColor(
        transitionSpec = {
            tween(1000)
        },
        label = "Color"
    ) { state ->
        if (state > 300)
            Color.Red
        else
            Color.Green
    }
    Text(text = "Visitors: ${animatedCount.value}", fontSize = 30.sp)
    Spacer(
        modifier = Modifier
            .height(40.dp)
            .width(animatedWidth.value)
            .background(animatedColor.value)
    )
}