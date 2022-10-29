package ru.korobeynikov.p17pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState

class EmployeePagingSource(private val employeeStorage: EmployeeStorage) : PagingSource<Int, Employee>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee> {
        return LoadResult.Page(employeeStorage.storage, null, null)
    }

    override fun getRefreshKey(state: PagingState<Int, Employee>): Int {
        return 0
    }
}