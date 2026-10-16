package ru.korobeynikov.p04viewmodel.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.p04viewmodel.viewmodels.MyViewModel
import ru.korobeynikov.p04viewmodel.viewmodels.MyViewModelFactory
import ru.korobeynikov.p04viewmodel.viewmodels.ViewModelWithContext

@Composable
fun MainScreen(
    myViewModel: MyViewModel = viewModel(factory = MyViewModelFactory()),
    viewModelWithContext: ViewModelWithContext
) {
    val data by myViewModel.data.collectAsState()
    myViewModel.loadData()
    val context = viewModelWithContext.getContext()
    Column {
        Text("data from data repository = $data")
        Text("context from view model = $context")
    }
}