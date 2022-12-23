package ru.korobeynikov.p09espressoidlingresource

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun whenClickButton_thenShowData() {
        var idlingResource: IdlingResource? = null
        activityTestRule.scenario.onActivity { activity ->
            idlingResource = activity.getIdlingResource()
        }
        registerIdlingResources(idlingResource)
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.text)).check(matches(withText("Data string")))
        unregisterIdlingResources(idlingResource)
    }
}