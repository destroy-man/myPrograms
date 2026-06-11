package ru.korobeynikov.p1161intenttasks1

import android.content.Intent

class ActivityB : MainActivity() {
    override fun onClick() {
        startActivity(Intent(this, ActivityC::class.java))
    }
}