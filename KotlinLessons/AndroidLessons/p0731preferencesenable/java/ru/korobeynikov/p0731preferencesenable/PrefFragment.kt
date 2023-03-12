package ru.korobeynikov.p0731preferencesenable

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

class PrefFragment : PreferenceFragmentCompat() {

    companion object {
        const val FRAGMENT_TAG = "prefFragment"
    }

    private var chb3: CheckBoxPreference? = null
    private var categ2: PreferenceCategory? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)

        chb3 = findPreference("chb3")
        categ2 = findPreference("categ2")
        categ2?.isEnabled = chb3?.isChecked == true
        chb3?.setOnPreferenceClickListener {
            categ2?.isEnabled = chb3?.isChecked == true
            false
        }
        val screen = findPreference<PreferenceScreen>("screen")
        val chb2 = findPreference<CheckBoxPreference>("chb2")
        screen?.dependency = chb2?.key
    }
}