package com.korobeynikov.p0731preferencesenable

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceCategory

class PrefActivity:PreferenceActivity() {

    lateinit var chb3:CheckBoxPreference
    lateinit var categ2:PreferenceCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref)
        chb3=findPreference("chb3") as CheckBoxPreference
        categ2=findPreference("categ2") as PreferenceCategory
        categ2.isEnabled=chb3.isChecked
        chb3.onPreferenceClickListener=Preference.OnPreferenceClickListener{
            categ2.isEnabled=chb3.isChecked
            false
        }
    }
}