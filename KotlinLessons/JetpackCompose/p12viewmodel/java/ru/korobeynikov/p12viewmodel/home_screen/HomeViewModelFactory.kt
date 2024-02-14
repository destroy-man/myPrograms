package ru.korobeynikov.p12viewmodel.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p12viewmodel.SomeRepository
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val someRepository: SomeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(someRepository) as T
    }
}