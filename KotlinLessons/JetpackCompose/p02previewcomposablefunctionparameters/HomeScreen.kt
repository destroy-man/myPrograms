package ru.korobeynikov.p02previewcomposablefunctionparameters

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Text(text = "Home screen", fontSize = 22.sp, color = Color.Green)
}

@Composable
fun HomeScreenWithUserName(userName: String) {
    Text("Home screen for $userName")
}

@Preview(showBackground = true, backgroundColor = 0xFFFF0000)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}