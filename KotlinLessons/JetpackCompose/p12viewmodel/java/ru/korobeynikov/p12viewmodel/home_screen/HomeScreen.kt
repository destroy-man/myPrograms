package ru.korobeynikov.p12viewmodel.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        onCounterClick = homeViewModel::onCounterClick,
        onEnabledChange = homeViewModel::setEnabled
    )
}

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    onCounterClick: () -> Unit,
    onEnabledChange: (Boolean) -> Unit,
) =
    Column {
        ClickCounter(count = uiState.count, onCounterClick = onCounterClick)
        EnableFeature(enabled = uiState.enabled, onEnabledChange = onEnabledChange)
    }

@Composable
fun ClickCounter(count: Int, onCounterClick: () -> Unit) =
    Text(text = "Clicks: $count", modifier = Modifier.clickable(onClick = onCounterClick))

@Composable
fun EnableFeature(enabled: Boolean, onEnabledChange: (Boolean) -> Unit) =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = enabled, onCheckedChange = onEnabledChange)
        Text(text = "enable feature")
    }