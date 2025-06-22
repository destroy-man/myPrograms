package ru.korobeynikov.p05backgroundandborderimages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HomeScreen() {
    AsyncImage(
        model = "https://developer.android.com/images/android-go/next-billion-users_856.png",
        contentDescription = null
    )
}

@Composable
fun HomeScreenBackgroundRectangle() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(color = Color.Cyan)
        )
    }
}

@Composable
fun HomeScreenBackgroundRoundedCorner() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(color = Color.Cyan, shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun HomeScreenBackgroundCutCorner() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(color = Color.Cyan, shape = CutCornerShape(16.dp))
        )
    }
}

@Composable
fun HomeScreenBackgroundCircle() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(color = Color.Cyan, shape = CircleShape)
        )
    }
}

@Composable
fun HomeScreenBackgroundBrush() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green
                        )
                    ),
                    alpha = 0.2f,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun HomeScreenBorderColor() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun HomeScreenBorderBrush() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun HomeScreenBackgroundBorder() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(120.dp)
                .background(color = Color.Cyan, shape = RoundedCornerShape(16.dp))
                .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape)
        )
    }
}

@Composable
fun HomeScreenImageIcon() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}