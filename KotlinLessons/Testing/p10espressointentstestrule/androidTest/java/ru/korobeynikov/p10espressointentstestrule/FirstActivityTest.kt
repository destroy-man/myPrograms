package ru.korobeynikov.p10espressointentstestrule

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test

class FirstActivityTest {

    @get:Rule
    var activityTestRule = IntentsTestRule(FirstActivity::class.java)

    @Test
    fun handleFailedStartActivityResult() {
        val result = Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null)
        intending(
            allOf(
                hasExtra(equalTo(Constants.EXTRA_ID), equalTo(1)),
                hasExtra(equalTo(Constants.EXTRA_NAME), equalTo("Name 1"))
            )
        ).respondWith(result)
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.text)).check(matches(withText("Cancel")))
    }
}