package ru.korobeynikov.p08espressoadapterviewrecyclerview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listItemClick() {
        onView(withId(R.id.recyclerView)).perform(actionOnItem<MyAdapter.ViewHolder>
            (hasDescendant(withText(containsString("Item 5"))), click()).atPosition(3))
        onView(withId(R.id.textView)).check(matches(withText("Item 52")))
    }
}