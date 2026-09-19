package ru.korobeynikov.p2modulesscope.database

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(private val context: Context)