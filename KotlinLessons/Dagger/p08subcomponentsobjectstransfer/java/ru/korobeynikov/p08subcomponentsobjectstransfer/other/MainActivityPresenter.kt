package ru.korobeynikov.p08subcomponentsobjectstransfer.other

import android.app.Activity
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val databaseHelper: DatabaseHelper,
    private val networkUtils: NetworkUtils,
    private val activity: Activity
)