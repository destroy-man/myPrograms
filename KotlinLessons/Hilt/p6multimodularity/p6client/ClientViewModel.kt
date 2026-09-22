package ru.korobeynikov.p6client

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.korobeynikov.p6data.Database
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(database: Database) : ViewModel() {
    private val _databaseState = MutableStateFlow(database)
    val databaseState: StateFlow<Database> = _databaseState
}