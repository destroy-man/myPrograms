package ru.korobeynikov.p12viewmodelhilt

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: SomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    fun onCounterClick() {
        _uiState.update {
            it.copy(count = it.count + 1)
        }
    }

    fun setEnabled(enabled: Boolean) {
        _uiState.update {
            it.copy(enabled = enabled)
        }
    }
}