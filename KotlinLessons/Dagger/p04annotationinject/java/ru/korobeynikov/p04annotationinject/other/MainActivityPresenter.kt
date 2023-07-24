package ru.korobeynikov.p04annotationinject.other

import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val databaseHelper: DatabaseHelper)