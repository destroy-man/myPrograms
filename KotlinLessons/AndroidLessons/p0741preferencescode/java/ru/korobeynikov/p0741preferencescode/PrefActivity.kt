package ru.korobeynikov.p0741preferencescode

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.ListPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceCategory

class PrefActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootScreen=preferenceManager.createPreferenceScreen(this)
        preferenceScreen=rootScreen
        val chb1=CheckBoxPreference(this)
        chb1.key="chb1"
        chb1.title="CheckBox 1"
        chb1.summaryOn="Description of checkbox 1 on"
        chb1.summaryOff="Description of checkbox 1 off"
        rootScreen.addPreference(chb1)
        val list=ListPreference(this)
        list.key="list"
        list.title="List"
        list.summary="Description of list"
        list.setEntries(R.array.entries)
        list.setEntryValues(R.array.entry_values)
        rootScreen.addPreference(list)
        val chb2=CheckBoxPreference(this)
        chb2.key="chb2"
        chb2.title="CheckBox 2"
        chb2.summary="Description of checkbox 2"
        rootScreen.addPreference(chb2)
        val screen=preferenceManager.createPreferenceScreen(this)
        screen.key="screen"
        screen.title="Screen"
        screen.summary="Description of screen"
        val chb3=CheckBoxPreference(this)
        chb3.key="chb3"
        chb3.title="CheckBox 3"
        chb3.summary="Description of checkbox 3"
        screen.addPreference(chb3)
        val categ1=PreferenceCategory(this)
        categ1.key="categ1"
        categ1.title="Category 1"
        categ1.summary="Description of category 1"
        screen.addPreference(categ1)
        val chb4=CheckBoxPreference(this)
        chb4.key="chb4"
        chb4.title="CheckBox 4"
        chb4.summary="Description of checkbox 4"
        categ1.addPreference(chb4)
        val categ2=PreferenceCategory(this)
        categ2.key="categ2"
        categ2.title="Category 2"
        categ2.summary="Description of category 2"
        screen.addPreference(categ2)
        val chb5=CheckBoxPreference(this)
        chb5.key="chb5"
        chb5.title="CheckBox 5"
        chb5.summary="Description of checkbox 5"
        categ2.addPreference(chb5)
        val chb6=CheckBoxPreference(this)
        chb6.key="chb6"
        chb6.title="CheckBox 6"
        chb6.summary="Description of checkbox 6"
        categ2.addPreference(chb6)
        rootScreen.addPreference(screen)
        list.dependency="chb1"
        screen.dependency="chb2"
        categ2.isEnabled=chb3.isChecked
        chb3.setOnPreferenceClickListener {
            categ2.isEnabled=chb3.isChecked
            false
        }
    }
}