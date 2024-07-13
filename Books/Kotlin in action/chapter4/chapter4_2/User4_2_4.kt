package ru.korobeynikov.chapter4.chapter4_2

import android.util.Log

class User4_2_4(private val name: String) {
    var address: String = "unspecified"
        set(value) {
            Log.d("myLogs", """Address was changed for $name: "$field" -> "$value".""".trimIndent())
            field = value
        }
}