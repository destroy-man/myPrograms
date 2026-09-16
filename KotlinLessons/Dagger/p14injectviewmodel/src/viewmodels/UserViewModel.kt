package ru.korobeynikov.p14injectviewmodel.viewmodels

import androidx.lifecycle.ViewModel
import ru.korobeynikov.p14injectviewmodel.database.DatabaseHelper

class UserViewModel(private val databaseHelper: DatabaseHelper) : ViewModel()