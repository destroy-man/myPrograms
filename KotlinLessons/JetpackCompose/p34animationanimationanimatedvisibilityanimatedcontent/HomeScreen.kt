package ru.korobeynikov.p34animationanimationanimatedvisibilityanimatedcontent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    //animateContentSize
    Box(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "${homeViewModel.visitorCount.collectAsState().value}",
            fontSize = 30.sp,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .animateContentSize(tween(1000))
        )
    }
}

@Composable
fun HomeScreenAnimate() {
    val scope = rememberCoroutineScope()
    var counter by remember { mutableIntStateOf(0) }
    Text(text = "Count $counter", fontSize = 30.sp, modifier = Modifier.clickable {
        scope.launch {
            animate(
                typeConverter = Int.VectorConverter,
                initialValue = counter,
                targetValue = counter + 100,
                animationSpec = tween(1000)
            ) { value, _ ->
                counter = value
            }
        }
    })
}

@Composable
fun HomeScreenCrossfade(homeViewModel: HomeViewModel = hiltViewModel()) {
    Crossfade(
        targetState = homeViewModel.visitorCount.collectAsState().value,
        animationSpec = tween(1000),
        label = "Visitors"
    ) { visitors ->
        Text("$visitors", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenAnimatedContentSlide(homeViewModel: HomeViewModel = hiltViewModel()) {
    AnimatedContent(
        targetState = homeViewModel.visitorCount.collectAsState().value,
        label = "Visitors",
        transitionSpec = {
            if (initialState < targetState) {
                slideInVertically {
                    it
                } togetherWith (slideOutVertically {
                    -it
                })
            } else {
                slideInVertically {
                    -it
                } togetherWith (slideOutVertically {
                    it
                })
            }
        }
    ) { count ->
        Text("$count", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenWithoutAnimatedContent(homeViewModel: HomeViewModel = hiltViewModel()) {
    Text("${homeViewModel.visitorCount.collectAsState().value}", fontSize = 30.sp)
}

@Composable
fun HomeScreenWithAnimatedContent(homeViewModel: HomeViewModel = hiltViewModel()) {
    AnimatedContent(
        targetState = homeViewModel.visitorCount.collectAsState().value,
        label = "Visitors"
    ) { count ->
        Text("$count", fontSize = 30.sp)
    }
}

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun HomeScreenTransitionWithAnimatedVisibility(homeViewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val transition = updateTransition(
            targetState = homeViewModel.visitorCount.collectAsState().value,
            label = "Visitors"
        )
        val animatedCount = transition.animateInt(transitionSpec = {
            tween(1000)
        }, label = "Count") { state ->
            state
        }
        val animatedWidth = transition.animateDp(transitionSpec = {
            tween(1000)
        }, label = "Width") { state ->
            (state / 500f * 300f).dp
        }
        val alertTransition = transition.createChildTransition { state ->
            state > 300
        }
        val animatedColor = alertTransition.animateColor(transitionSpec = {
            tween(1000)
        }, label = "Color") { state ->
            if (state) Color.Red else Color.Green
        }

        Text(text = "Visitors: ${animatedCount.value}", fontSize = 30.sp)
        Spacer(
            modifier = Modifier
                .height(40.dp)
                .width(animatedWidth.value)
                .background(animatedColor.value)
        )
        alertTransition.AnimatedVisibility(
            visible = { state ->
                state
            },
            enter = expandHorizontally(tween(1000)),
            exit = shrinkHorizontally(tween(1000))
        ) {
            Text("Alert", fontSize = 30.sp)
        }
    }
}

@Composable
fun HomeScreenAnimatedVisibilityWithTransition() {
    val durationMillis = 2000
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        AnimatedVisibility(
            visible = checked,
            enter = expandVertically(tween(durationMillis)),
            exit = shrinkVertically(tween(durationMillis))
        ) {
            val color by transition.animateColor(transitionSpec = {
                tween(durationMillis)
            }, label = "Color") { state ->
                if (state == EnterExitState.Visible) Color.Green else Color.Red
            }
            Text("Some description", fontSize = 30.sp, color = color)
        }
    }
}

@Composable
fun HomeScreenAnimatedVisibilityEnterExitNone() {
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        AnimatedVisibility(
            visible = checked,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Text("Some description", fontSize = 30.sp)
        }
    }
}

@Composable
fun HomeScreenFadeSlideDuration1000() {
    val durationMillis = 1000
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(tween(durationMillis)),
            exit = fadeOut(tween(durationMillis))
        ) {
            Row {
                Text(
                    text = "1", fontSize = 30.sp, modifier = Modifier.animateEnterExit(
                        enter = slideInHorizontally(tween(durationMillis)) {
                            -it
                        },
                        exit = slideOutHorizontally(tween(durationMillis)) {
                            -it
                        }
                    )
                )
                Text(
                    text = "2", fontSize = 30.sp, modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(tween(durationMillis)) {
                            -it
                        },
                        exit = slideOutVertically(tween(durationMillis)) {
                            -it
                        }
                    )
                )
                Text(
                    text = "3", fontSize = 30.sp, modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(tween(durationMillis)) {
                            it
                        },
                        exit = slideOutVertically(tween(durationMillis)) {
                            it
                        }
                    )
                )
                Text(
                    text = "4", fontSize = 30.sp, modifier = Modifier.animateEnterExit(
                        enter = slideInHorizontally(tween(durationMillis)) {
                            it
                        },
                        exit = slideOutHorizontally(tween(durationMillis)) {
                            it
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun HomeScreenFadeExpandShrinkDuration2000() {
    val durationMillis = 2000
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(tween(durationMillis)) + expandVertically(tween(durationMillis)),
            exit = fadeOut(tween(durationMillis)) + shrinkVertically(tween(durationMillis))
        ) {
            Text("Some description", fontSize = 30.sp)
        }
    }
}

@Composable
fun HomeScreenWithoutAnimatedVisibility() {
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        if (checked) Text("Some description", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenWithAnimatedVisibility() {
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
        AnimatedVisibility(checked) {
            Text("Some description", fontSize = 30.sp)
        }
    }
}