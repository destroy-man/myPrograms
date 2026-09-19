package ru.korobeynikov.p3injectviewmodel.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.korobeynikov.p3injectviewmodel.Utils
import ru.korobeynikov.p3injectviewmodel.database.DatabaseHelper
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(databaseHelper: DatabaseHelper, utils: Utils) :
    ViewModel() {

    private val _state = MutableStateFlow<MainState?>(null)
    val state: StateFlow<MainState?> = _state

    init {
        _state.value = MainState(databaseHelper, utils)
    }
}