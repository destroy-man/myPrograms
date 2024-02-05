package ru.korobeynikov.p02previewcomposablefunctionparameters

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() = Text(text = "Home screen", fontSize = 22.sp, color = Color.Green)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() = HomeScreen()