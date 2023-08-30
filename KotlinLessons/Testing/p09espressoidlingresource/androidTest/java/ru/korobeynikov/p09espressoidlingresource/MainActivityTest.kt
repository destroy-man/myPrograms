package ru.korobeynikov.p09espressoidlingresource

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var idlingResource: IdlingResource

    @Test
    fun whenClickButton_thenShowData() {
        activityTestRule.scenario.onActivity { mainActivity ->
            idlingResource = mainActivity.getIdlingResource()
            IdlingRegistry.getInstance().register(idlingResource)
        }
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.text)).check(matches(withText("Data string")))
        activityTestRule.scenario.onActivity {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }
}