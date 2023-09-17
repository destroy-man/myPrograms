package ru.korobeynikov.p14paginglibrarybasics.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class EmployeeViewModel : ViewModel() {

    companion object {
        private const val ITEMS_PER_PAGE = 20
    }

    val employees = Pager(config = PagingConfig(pageSize = ITEMS_PER_PAGE,
        enablePlaceholders = false), pagingSourceFactory = { EmployeePagingSource() }).flow
        .cachedIn(viewModelScope)
}