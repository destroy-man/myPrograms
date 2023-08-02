package ru.korobeynikov.p14fragmentviewmodel.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val networkUtils: NetworkUtils,
                                           private val databaseHelper: DatabaseHelper) :
                                                ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ViewModel1::class.java -> ViewModel1(networkUtils)
            ViewModel2::class.java -> ViewModel2(databaseHelper)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}