package ru.korobeynikov.p12viewmodel.home_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.korobeynikov.p12viewmodel.SomeRepository

class HomeViewModel(private val repository: SomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    fun onCounterClick() = _uiState.update {
        it.copy(count = it.count + 1)
    }

    fun setEnabled(enabled: Boolean) = _uiState.update {
        it.copy(enabled = enabled)
    }
}