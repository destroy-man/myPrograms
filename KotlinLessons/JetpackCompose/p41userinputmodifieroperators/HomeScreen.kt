package ru.korobeynikov.p41userinputmodifieroperators

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    //auto scroll to upper bound and bottom bound box
    val size = remember { mutableFloatStateOf(100f) }
    val scope = rememberCoroutineScope()
    val connection = remember { MyNestedScrollConnection(size, scope) }

    Column(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(connection)) {
        Box(modifier = Modifier
            .size(size.floatValue.px)
            .background(Color.LightGray)
        )
        LazyColumn {
            items(50) {
                Text("Item $it", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun HomeScreenIncreaseAndDecreaseBox() {
    val size = remember { mutableFloatStateOf(200f) }
    val connection = remember { MyNestedScrollConnectionIncreaseAndDecreaseBox(size) }

    Column(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(connection)) {
        Box(modifier = Modifier
            .size(size.floatValue.px)
            .background(Color.LightGray)
        )
        LazyColumn {
            items(50) {
                Text("Item $it", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

inline val Float.px: Dp
    @Composable get() = with(LocalDensity.current) { this@px.toDp() }

@Composable
fun HomeScreenConnectionDefault() {
    val connection = remember { MyNestedScrollConnectionAvailableTimes() }

    Column(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(connection)) {
        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.LightGray)
        )
        LazyColumn {
            items(50) {
                Text("Item $it", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun HomeScreenTransformable() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(100f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .transformable(
                state = remember {
                    TransformableState { zoomChange, panChange, rotationChange ->
                        offset += panChange
                        scale *= zoomChange
                        rotation += rotationChange
                    }
                }
            )
            .drawWithContent {
                drawContent()
                rotate(rotation, pivot = offset) {
                    drawPoints(listOf(offset), PointMode.Points, Color.LightGray, scale)
                }
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenAnchoredDraggableStateAnimateTo() {
    Box(modifier = Modifier.fillMaxSize()) {
        val decaySpec = rememberSplineBasedDecay<Float>()
        val state = remember {
            AnchoredDraggableState(
                initialValue = "A",
                anchors = DraggableAnchors {
                    "A" at 0f
                    "B" at 600f
                    "C" at 1200f
                },
                positionalThreshold = { it * 0.5f },
                velocityThreshold = { 300f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decaySpec
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(0, state.requireOffset().toInt()) }
                .anchoredDraggable(state = state, orientation = Orientation.Vertical)
                .size(50.dp)
                .background(Color.LightGray)
        )

        LaunchedEffect(Unit) {
            delay(1000)
            state.animateTo("B")
            delay(500)
            state.animateTo("C")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenVelocityThreshold5000() {
    Box(modifier = Modifier.fillMaxSize()) {
        val decaySpec = rememberSplineBasedDecay<Float>()
        val state = remember {
            AnchoredDraggableState(
                initialValue = "A",
                anchors = DraggableAnchors {
                    "A" at 0f
                    "B" at 600f
                },
                positionalThreshold = { it * 0.9f },
                velocityThreshold = { 5000f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decaySpec
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(0, state.requireOffset().toInt()) }
                .anchoredDraggable(state = state, orientation = Orientation.Vertical)
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenVelocityThreshold1() {
    Box(modifier = Modifier.fillMaxSize()) {
        val decaySpec = rememberSplineBasedDecay<Float>()
        val state = remember {
            AnchoredDraggableState(
                initialValue = "A",
                anchors = DraggableAnchors {
                    "A" at 0f
                    "B" at 600f
                },
                positionalThreshold = { it * 0.9f },
                velocityThreshold = { 1f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decaySpec
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(0, state.requireOffset().toInt()) }
                .anchoredDraggable(state = state, orientation = Orientation.Vertical)
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenPositionalThreshold01() {
    Box(modifier = Modifier.fillMaxSize()) {
        val decaySpec = rememberSplineBasedDecay<Float>()
        val state = remember {
            AnchoredDraggableState(
                initialValue = "A",
                anchors = DraggableAnchors {
                    "A" at 0f
                    "B" at 600f
                },
                positionalThreshold = { it * 0.1f },
                velocityThreshold = { 300f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decaySpec
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(0, state.requireOffset().toInt()) }
                .anchoredDraggable(state = state, orientation = Orientation.Vertical)
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenPositionalThreshold05() {
    Box(modifier = Modifier.fillMaxSize()) {
        val decaySpec = rememberSplineBasedDecay<Float>()
        val state = remember {
            AnchoredDraggableState(
                initialValue = "A",
                anchors = DraggableAnchors {
                    "A" at 0f
                    "B" at 600f
                },
                positionalThreshold = { it * 0.5f },
                velocityThreshold = { 300f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decaySpec
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(0, state.requireOffset().toInt()) }
                .anchoredDraggable(state = state, orientation = Orientation.Vertical)
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenDraggable2D() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offset by remember { mutableStateOf(Offset.Zero) }
        Box(
            modifier = Modifier
                .offset { IntOffset(x = offset.x.toInt(), offset.y.toInt()) }
                .draggable2D(
                    state = rememberDraggable2DState { offset += it }
                )
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun HomeScreenDraggableAnimateDecay() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offset by remember { mutableFloatStateOf(0f) }
        var color by remember { mutableStateOf(Color.LightGray) }
        val decaySpec = rememberSplineBasedDecay<Float>()
        Box(
            modifier = Modifier
                .offset { IntOffset(x = offset.toInt(), 0) }
                .draggable(
                    state = rememberDraggableState { offset += it },
                    orientation = Orientation.Horizontal,
                    onDragStarted = { color = Color.DarkGray },
                    onDragStopped = { velocity ->
                        color = Color.LightGray
                        Animatable(offset)
                            .apply { updateBounds(0f, 800f) }
                            .animateDecay(velocity, decaySpec) {
                                offset = value
                            }
                    }
                )
                .size(100.dp)
                .background(color)
        )
    }
}

@Composable
fun HomeScreenDraggable() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offset by remember { mutableFloatStateOf(0f) }
        Box(
            modifier = Modifier
                .offset { IntOffset(x = offset.toInt(), 0) }
                .draggable(
                    state = rememberDraggableState { offset += it },
                    orientation = Orientation.Horizontal,
                    startDragImmediately = false
                )
                .size(50.dp)
                .background(Color.LightGray)
        )
    }
}