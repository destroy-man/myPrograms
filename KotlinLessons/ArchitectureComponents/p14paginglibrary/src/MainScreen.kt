package ru.korobeynikov.p14paginglibrary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import ru.korobeynikov.p14paginglibrary.employee.EmployeeViewModel
import ru.korobeynikov.p14paginglibrary.employee.EmployeeViewModelFactory

@Composable
fun MainScreen(viewModel: EmployeeViewModel = viewModel(factory = EmployeeViewModelFactory())) {
    val employees = viewModel.pagerFlow.collectAsLazyPagingItems()
    if (employees.loadState.refresh is LoadState.Loading && employees.itemCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(employees.itemCount, key = employees.itemKey { employee ->
                employee.id
            }) { index ->
                Text(employees[index].toString())
            }
        }
    }
}