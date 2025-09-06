package ru.korobeynikov.p12viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p12viewmodel.SomeRepository
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val repository: SomeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == HomeViewModelDagger::class.java) {
            return HomeViewModelDagger(repository = repository) as T
        }
        return super.create(modelClass)
    }
}