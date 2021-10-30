package com.korobeynikov.p1161mngtasks1

import android.content.Intent
import android.view.View

class ActivityC:MainActivity() {
    override fun onClick(v: View) {
        startActivity(Intent(this,ActivityD::class.java))
    }
}