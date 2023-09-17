package ru.korobeynikov.p14paginglibrarybasics.employee

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import kotlin.math.max

class EmployeePagingSource : PagingSource<Int, Employee>() {

    companion object {
        private const val STARTING_KEY = 1
        private const val LOAD_DELAY_MILLIS = 3000L
    }

    override fun getRefreshKey(state: PagingState<Int, Employee>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val employee = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(employee.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)
        if (start != STARTING_KEY) delay(LOAD_DELAY_MILLIS)
        return LoadResult.Page(
            data = range.map { number ->
                Employee(number, "Employee $number", 1000 * number)
            },
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(range.first - params.loadSize)
            },
            nextKey =
                if (range.last == 100) null
                else range.last + 1
        )
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}