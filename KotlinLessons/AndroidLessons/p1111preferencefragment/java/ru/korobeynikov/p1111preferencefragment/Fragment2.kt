package ru.korobeynikov.p1111preferencefragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class Fragment2 : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref2, rootKey)
    }
}