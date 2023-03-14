package ru.korobeynikov.p0741preferencescode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

class PrefActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            var fragment = supportFragmentManager.findFragmentByTag(PrefFragment.FRAGMENT_TAG)
            if (fragment == null)
                fragment = PrefFragment()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment, PrefFragment.FRAGMENT_TAG).commit()
        }
    }

    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat,
                                         preferenceScreen: PreferenceScreen): Boolean {
        val fragment = PrefFragment()
        val args = Bundle()
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preferenceScreen.key)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment,
            preferenceScreen.key).addToBackStack(preferenceScreen.key).commit()
        return true
    }
}