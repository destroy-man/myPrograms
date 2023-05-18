package ru.korobeynikov.p1161mngtasks1

import android.content.Intent

class ActivityB : MainActivity() {
    override fun clickButton() {
        startActivity(Intent(this, ActivityC::class.java))
    }
}