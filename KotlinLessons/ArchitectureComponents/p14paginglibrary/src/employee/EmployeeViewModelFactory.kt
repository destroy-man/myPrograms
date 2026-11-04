package ru.korobeynikov.p14paginglibrary.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmployeeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(EmployeeRepository()) as T
    }
}