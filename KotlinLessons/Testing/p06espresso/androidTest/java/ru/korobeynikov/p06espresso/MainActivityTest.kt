package ru.korobeynikov.p06espresso

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun changeTextCorrect() {
        val text = "my test text"
        onView(withId(R.id.editText)).perform(typeText(text))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText(text)))
    }

    @Test
    fun changeTextEmpty() {
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText(R.string.empty_text)))
    }
}