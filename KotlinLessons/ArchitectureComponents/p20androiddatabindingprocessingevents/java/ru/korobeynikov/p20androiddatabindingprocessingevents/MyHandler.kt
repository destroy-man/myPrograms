package ru.korobeynikov.p20androiddatabindingprocessingevents

import android.content.Context
import android.widget.Toast

class MyHandler {
    fun onEnabled(employee: Employee, enabled: Boolean, context: Context) {
        if (enabled)
            Toast.makeText(context, "$employee", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "not enabled", Toast.LENGTH_SHORT).show()
    }
}