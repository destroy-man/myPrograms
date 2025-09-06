package ru.korobeynikov.p12viewmodel

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
import androidx.compose.ui.tooling.preview.Preview
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
) {
    Column {
        ClickCounter(count = uiState.count, onCounterClick = onCounterClick)
        EnableFeature(enabled = uiState.enabled, onEnabledChange = onEnabledChange)
    }
}

@Composable
fun HomeScreenManyStates(homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    Column {
        ClickCounter(count = uiState.count, onCounterClick = homeViewModel::onCounterClick)
        EnableFeature(enabled = uiState.enabled, onEnabledChange = homeViewModel::setEnabled)
    }
}

@Composable
fun ClickCounter(count: Int, onCounterClick: () -> Unit) {
    Text(
        text = "Clicks: $count",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
}

@Composable
fun EnableFeature(enabled: Boolean, onEnabledChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = enabled, onCheckedChange = onEnabledChange)
        Text("enable feature")
    }
}

@Composable
fun HomeScreenComposeState(homeViewModel: HomeViewModelComposeState = viewModel()) {
    val counter by homeViewModel.counter
    Text(
        text = "Clicks: $counter",
        modifier = Modifier.clickable(onClick = homeViewModel::onCounterClick)
    )
}

@Preview(showBackground = true)
@Composable
fun ClickCounterPreview() {
    ClickCounter(count = 5, onCounterClick = {})
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeScreenUiState(count = 5, enabled = true),
        onCounterClick = {},
        onEnabledChange = {}
    )
}