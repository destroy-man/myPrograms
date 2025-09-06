package ru.korobeynikov.p12viewmodel.dagger

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
import ru.korobeynikov.p12viewmodel.HomeScreenUiState

@Composable
fun HomeScreenDagger(homeViewModel: HomeViewModelDagger = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreenDagger(
        uiState = uiState,
        onCounterClick = homeViewModel::onCounterClick,
        onEnabledChange = homeViewModel::setEnabled
    )
}

@Composable
fun HomeScreenDagger(
    uiState: HomeScreenUiState,
    onCounterClick: () -> Unit,
    onEnabledChange: (Boolean) -> Unit,
) {
    Column {
        ClickCounterDagger(count = uiState.count, onCounterClick = onCounterClick)
        EnableFeatureDagger(enabled = uiState.enabled, onEnabledChange = onEnabledChange)
    }
}

@Composable
fun ClickCounterDagger(count: Int, onCounterClick: () -> Unit) {
    Text(
        text = "Clicks: $count",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
}

@Composable
fun EnableFeatureDagger(enabled: Boolean, onEnabledChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = enabled, onCheckedChange = onEnabledChange)
        Text("enable feature")
    }
}