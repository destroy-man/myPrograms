package ru.korobeynikov.p17pagingsource

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData

class EmployeeViewModel : ViewModel() {

    companion object {
        private const val ITEMS_PER_SIZE = 30
    }

    val items = Pager(config = PagingConfig(pageSize = ITEMS_PER_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { EmployeePagingSource(EmployeeStorage()) }).liveData
}