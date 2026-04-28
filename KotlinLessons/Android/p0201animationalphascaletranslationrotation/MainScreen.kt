package ru.korobeynikov.p0201animationalphascaletranslationrotation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val alphaAnim = remember { Animatable(1f) }
    val scaleXAnim = remember { Animatable(1f) }
    val scaleYAnim = remember { Animatable(1f) }
    val transXAnim = remember { Animatable(0f) }
    val transYAnim = remember { Animatable(0f) }
    val rotateAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Column {
        Text(
            "Text",
            fontSize = 38.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    alpha = alphaAnim.value
                    scaleX = scaleXAnim.value
                    scaleY = scaleYAnim.value
                    translationX = transXAnim.value
                    translationY = transYAnim.value
                    rotationX = rotateAnim.value
                    rotationY = rotateAnim.value
                }
        )
        FlowRow {
            Button(onClick = {
                scope.launch {
                    alphaAnim.animateTo(
                        if (alphaAnim.value == 1f) 0f else 1f,
                        animationSpec = tween(3000)
                    )
                }
            }) {
                Text("alpha")
            }
            Button(onClick = {
                scope.launch {
                    scaleXAnim.animateTo(
                        if (scaleXAnim.value == 1f) 0.1f else 1f,
                        animationSpec = tween(3000)
                    )
                }
                scope.launch {
                    scaleYAnim.animateTo(
                        if (scaleYAnim.value == 1f) 0.1f else 1f,
                        animationSpec = tween(3000)
                    )
                }
            }) {
                Text("scale")
            }
            Button(onClick = {
                scope.launch {
                    transXAnim.animateTo(
                        if (transXAnim.value == 0f) -150f else 0f,
                        animationSpec = tween(3000)
                    )
                }
                scope.launch {
                    transYAnim.animateTo(
                        if (transYAnim.value == 0f) -200f else 0f,
                        animationSpec = tween(3000)
                    )
                }
            }) {
                Text("translate")
            }
            Button(onClick = {
                scope.launch {
                    rotateAnim.animateTo(
                        if (rotateAnim.value == 0f) 180f else 0f,
                        animationSpec = tween(3000)
                    )
                }
            }) {
                Text("rotate")
            }
            Button(onClick = {
                scope.launch {
                    rotateAnim.animateTo(
                        if (rotateAnim.value == 0f) 180f else 0f,
                        animationSpec = tween(3000)
                    )
                }
                scope.launch {
                    scaleXAnim.animateTo(
                        if (scaleXAnim.value == 1f) 0.1f else 1f,
                        animationSpec = tween(3000)
                    )
                }
                scope.launch {
                    scaleYAnim.animateTo(
                        if (scaleYAnim.value == 1f) 0.1f else 1f,
                        animationSpec = tween(3000)
                    )
                }
            }) {
                Text("combo")
            }
        }
    }
}