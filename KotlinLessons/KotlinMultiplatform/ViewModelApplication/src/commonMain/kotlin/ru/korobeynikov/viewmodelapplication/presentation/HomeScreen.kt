package ru.korobeynikov.viewmodelapplication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>()) {
    val counter by homeViewModel.counter.collectAsState()
    Text(
        text = "Clicks: $counter",
        modifier = Modifier.clickable(onClick = homeViewModel::onCounterClick)
    )
}