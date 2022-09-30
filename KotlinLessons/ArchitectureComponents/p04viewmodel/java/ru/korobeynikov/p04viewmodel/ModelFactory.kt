package ru.korobeynikov.p04viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelFactory(private val id: Long) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MyViewModel::class.java)
            return MyViewModel(id) as T
        return super.create(modelClass)
    }
}