package ru.korobeynikov.p08espressoadapterviewrecyclerview

import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.hasEntry
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listItemClick() {
        onData(hasEntry(equalTo("nameItem"), `is`("Item 50"))).check(matches(isDisplayed()))
    }
}