package ru.korobeynikov.p14paginglibrary.employee

import androidx.paging.PagingSource
import androidx.paging.PagingState

class EmployeeDataSource(
    private val employeeRepository: EmployeeRepository,
    private val maxPage: Int
) : PagingSource<Int, Employee>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee> {
        val nextPage = params.key ?: 1
        val employees = employeeRepository.getItems(nextPage, params.loadSize)
        return LoadResult.Page(
            data = employees,
            prevKey = if (nextPage > 1) nextPage - 1 else null,
            nextKey = if (nextPage == maxPage) null else nextPage + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Employee>): Int? {
        return state.anchorPosition
    }
}