package ru.korobeynikov.p1161intenttasks1

import android.content.Intent

class ActivityA : MainActivity() {
    override fun onClick() {
        startActivity(Intent(this, ActivityB::class.java))
    }
}