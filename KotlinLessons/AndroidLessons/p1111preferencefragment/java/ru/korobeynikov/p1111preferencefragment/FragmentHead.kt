package ru.korobeynikov.p1111preferencefragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class FragmentHead : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_head, rootKey)
    }
}