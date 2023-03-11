package ru.korobeynikov.p0711preferencessimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PrefActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(android.R.id.content, PrefFragment()).commit()
    }
}