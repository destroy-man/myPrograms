package ru.korobeynikov.p13modifier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() = Column(modifier = Modifier.fillMaxWidth()) {
    Text(text = "Some text", modifier = Modifier.align(Alignment.CenterHorizontally))
}
