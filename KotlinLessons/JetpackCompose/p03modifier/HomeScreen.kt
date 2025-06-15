package ru.korobeynikov.p03modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Text(
        text = "Home screen",
        fontSize = 32.sp,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxWidth()
    )
}

@Composable
fun HomeScreenWithWidth150() {
    Text(
        text = "Home screen",
        fontSize = 32.sp,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .background(color = Color.Black)
            .width(150.dp)
    )
}