package ru.korobeynikov.p1161mngtasks1

import android.content.Intent
import android.view.View

class ActivityB:MainActivity(){
    override fun onClick(v: View) {
        startActivity(Intent(this,ActivityC::class.java))
    }
}