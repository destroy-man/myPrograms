package com.korobeynikov.p1111preferencefragment

import android.preference.PreferenceActivity

class MainActivity : PreferenceActivity() {
    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.pref_head,target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return true
    }
}