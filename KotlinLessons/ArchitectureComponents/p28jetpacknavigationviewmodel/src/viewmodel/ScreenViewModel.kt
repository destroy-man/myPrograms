package ru.korobeynikov.p28jetpacknavigationviewmodel.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(private val repository: ScreenRepository) : ViewModel()