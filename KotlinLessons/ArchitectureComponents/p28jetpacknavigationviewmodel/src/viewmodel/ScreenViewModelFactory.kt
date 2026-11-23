package ru.korobeynikov.p28jetpacknavigationviewmodel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScreenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScreenViewModel(ScreenRepository()) as T
    }
}