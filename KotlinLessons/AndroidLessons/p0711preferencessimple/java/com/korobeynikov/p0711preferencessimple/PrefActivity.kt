package com.korobeynikov.p0711preferencessimple

import android.os.Bundle
import android.preference.PreferenceActivity

class PrefActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref)
    }
}