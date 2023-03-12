package ru.korobeynikov.p0721preferencessimple2

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class PrefFragment : PreferenceFragmentCompat() {

    companion object {
        const val FRAGMENT_TAG = "prefFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)
    }
}