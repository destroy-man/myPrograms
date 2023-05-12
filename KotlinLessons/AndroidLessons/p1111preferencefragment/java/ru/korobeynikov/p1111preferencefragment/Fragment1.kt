package ru.korobeynikov.p1111preferencefragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class Fragment1 : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref1, rootKey)
    }
}