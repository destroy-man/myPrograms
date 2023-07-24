package ru.korobeynikov.p05objecttransmitmenttocomponent.other

import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val databaseHelper: DatabaseHelper)