package ru.korobeynikov.p1161mngtasks1

import android.content.Intent
import android.view.View

class ActivityA:MainActivity(){
    override fun onClick(v: View) {
        startActivity(Intent(this,ActivityB::class.java))
    }
}