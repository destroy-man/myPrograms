package com.korobeynikov.p0721preferencessimple2

import android.os.Bundle
import android.preference.PreferenceActivity

class PrefActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref)
    }
}