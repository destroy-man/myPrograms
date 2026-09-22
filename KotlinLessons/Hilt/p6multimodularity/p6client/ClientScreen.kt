package ru.korobeynikov.p6client

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ClientScreen(clientViewModel: ClientViewModel = viewModel()) {
    val database by clientViewModel.databaseState.collectAsState()
    Text("This is Client screen")
    Text("database = ${database.hashCode()}")
}