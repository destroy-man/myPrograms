package ru.korobeynikov.p3injectviewmodel.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val state by mainViewModel.state.collectAsState()
    val databaseHelper = state?.databaseHelper
    val utils = state?.utils
    Text(
        "${mainViewModel.javaClass.simpleName} = ${mainViewModel.hashCode()}, " +
                "${databaseHelper?.javaClass?.simpleName} = ${databaseHelper.hashCode()}, " +
                "${utils?.javaClass?.simpleName} = ${utils.hashCode()}"
    )
}