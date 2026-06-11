package ru.korobeynikov.p1161intenttasks1

import android.content.Intent

class ActivityD : MainActivity() {
    override fun onClick() {
        startActivity(Intent(this, ActivityD::class.java))
    }
}