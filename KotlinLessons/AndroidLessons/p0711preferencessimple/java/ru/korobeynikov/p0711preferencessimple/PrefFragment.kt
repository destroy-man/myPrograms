package ru.korobeynikov.p0711preferencessimple

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class PrefFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)
    }
}