package ru.korobeynikov.p0741preferencescode

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.preference.*

class PrefFragment : PreferenceFragmentCompat() {

    companion object {
        const val FRAGMENT_TAG = "prefFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if (rootKey == null)
            activity?.let {
                val rootScreen = preferenceManager.createPreferenceScreen(it.applicationContext)
                preferenceScreen = rootScreen

                val chb1 = CheckBoxPreference(it.applicationContext)
                chb1.key = "chb1"
                chb1.title = "CheckBox 1"
                chb1.summaryOn = "Description of checkbox 1 on"
                chb1.summaryOff = "Description of checkbox 1 off"
                rootScreen.addPreference(chb1)

                val list = ListPreference(it.applicationContext)
                list.key = "list"
                list.title = "List"
                list.summary = "Description of list"
                list.entries = resources.getStringArray(R.array.entries)
                list.entryValues = resources.getStringArray(R.array.entry_values)
                rootScreen.addPreference(list)

                val chb2 = CheckBoxPreference(it.applicationContext)
                chb2.key = "chb2"
                chb2.title = "CheckBox 2"
                chb2.summary = "Description of checkbox 2"
                rootScreen.addPreference(chb2)

                val screen = createAdditionalScreen(it)
                rootScreen.addPreference(screen)
                list.dependency = chb1.key
                screen.dependency = chb2.key
            }
        else
            activity?.let {
                preferenceScreen = createAdditionalScreen(it)
            }
    }

    private fun createAdditionalScreen(fragmentActivity: FragmentActivity): PreferenceScreen {
        val screen = preferenceManager.createPreferenceScreen(fragmentActivity.applicationContext)
        screen.key = "screen"
        screen.title = "Screen"
        screen.summary = "Description of screen"

        val chb3 = CheckBoxPreference(fragmentActivity.applicationContext)
        chb3.key = "chb3"
        chb3.title = "CheckBox 3"
        chb3.summary = "Description of checkbox 3"
        screen.addPreference(chb3)

        val categ1 = PreferenceCategory(fragmentActivity.applicationContext)
        categ1.key = "categ1"
        categ1.title = "Category 1"
        categ1.summary = "Description of category 1"
        screen.addPreference(categ1)

        val chb4 = CheckBoxPreference(fragmentActivity.applicationContext)
        chb4.key = "chb4"
        chb4.title = "CheckBox 4"
        chb4.summary = "Description of checkbox 4"
        categ1.addPreference(chb4)

        val categ2 = PreferenceCategory(fragmentActivity.applicationContext)
        categ2.key = "categ2"
        categ2.title = "Category 2"
        categ2.summary = "Description of category 2"
        screen.addPreference(categ2)

        val chb5 = CheckBoxPreference(fragmentActivity.applicationContext)
        chb5.key = "chb5"
        chb5.title = "CheckBox 5"
        chb5.summary = "Description of checkbox 5"
        categ2.addPreference(chb5)

        val chb6 = CheckBoxPreference(fragmentActivity.applicationContext)
        chb6.key = "chb6"
        chb6.title = "CheckBox 6"
        chb6.summary = "Description of checkbox 6"
        categ2.addPreference(chb6)

        categ2.isEnabled = chb3.isChecked
        chb3.setOnPreferenceClickListener {
            categ2.isEnabled = chb3.isChecked
            false
        }
        return screen
    }
}