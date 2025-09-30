package ru.korobeynikov.p15navigationandviewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: SomeRepository,
    @param:Named("User") private val savedState: SavedStateHandle,
) : ViewModel() {
    fun showUserId() {
        val userId = savedState.get<String>("userId")
        Log.d("myLogs", "user $userId")
    }
}