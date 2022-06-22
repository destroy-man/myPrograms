package ru.korobeynikov.p1161mngtasks1

import android.content.Intent
import android.view.View

class ActivityD:MainActivity(){
    override fun onClick(v: View) {
        startActivity(Intent(this,ActivityD::class.java))
    }
}