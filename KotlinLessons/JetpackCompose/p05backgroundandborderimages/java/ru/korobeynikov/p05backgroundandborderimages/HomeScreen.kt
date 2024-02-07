package ru.korobeynikov.p05backgroundandborderimages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun HomeScreen() = Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    AsyncImage(
        model = "https://developer.android.com/images/android-go/next-billion-users_856.png",
        contentDescription = null
    )
}