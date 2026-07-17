package ru.korobeynikov.p1571bitmap

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource

@Composable
fun MainScreen() {
    val bitmap = ImageBitmap.imageResource(R.drawable.box)
    Image(bitmap = bitmap, contentDescription = null)
}