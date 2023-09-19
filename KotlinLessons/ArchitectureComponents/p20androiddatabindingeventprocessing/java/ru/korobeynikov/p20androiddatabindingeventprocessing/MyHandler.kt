package ru.korobeynikov.p20androiddatabindingeventprocessing

import android.content.Context
import android.util.Log

class MyHandler {

    fun onEnabled(employee: Employee, enabled: Boolean, context: Context) =
        Log.d("myLogs", "employee = $employee, enabled = $enabled, context = $context")
}