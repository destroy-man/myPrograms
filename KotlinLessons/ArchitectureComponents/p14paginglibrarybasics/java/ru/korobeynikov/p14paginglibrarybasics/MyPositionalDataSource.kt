package ru.korobeynikov.p14paginglibrarybasics

import android.util.Log
import androidx.paging.PositionalDataSource

class MyPositionalDataSource(private val employeeStorage: EmployeeStorage) :
    PositionalDataSource<Employee>() {

    companion object {
        private const val TAG = "myLogs"
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Employee>) {
        Log.d(TAG, "loadInitial, requestedStartPosition = ${params.requestedStartPosition}, " +
                "requestedLoadSize = ${params.requestedLoadSize}")
        val result = employeeStorage.getData(params.requestedStartPosition, params.requestedLoadSize)
        callback.onResult(result, 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Employee>) {
        Log.d(TAG, "loadRange, startPosition = ${params.startPosition}, loadSize = ${params.loadSize}")
        val result = employeeStorage.getData(params.startPosition, params.loadSize)
        callback.onResult(result)
    }
}