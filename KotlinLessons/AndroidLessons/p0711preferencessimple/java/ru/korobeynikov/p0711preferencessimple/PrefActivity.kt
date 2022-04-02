package ru.korobeynikov.p0711preferencessimple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity

class PrefActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref)
    }
}