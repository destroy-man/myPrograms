package ru.korobeynikov.p34animationanimatedvisibilityanimatedcontent

import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    var counter by remember {
        mutableIntStateOf(0)
    }
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