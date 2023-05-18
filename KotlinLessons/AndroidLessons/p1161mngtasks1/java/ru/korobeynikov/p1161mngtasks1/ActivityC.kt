package ru.korobeynikov.p1161mngtasks1

import android.content.Intent

class ActivityC : MainActivity() {
    override fun clickButton() {
        startActivity(Intent(this, ActivityD::class.java))
    }
}