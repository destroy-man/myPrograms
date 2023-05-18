package ru.korobeynikov.p1161mngtasks1

import android.content.Intent

class ActivityA : MainActivity() {
    override fun clickButton() {
        startActivity(Intent(this, ActivityB::class.java))
    }
}