package ru.korobeynikov.p10espressorules

import android.app.Activity
import android.app.Instrumentation
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import org.hamcrest.CoreMatchers.*

@RunWith(AndroidJUnit4::class)
class FirstActivityTest {

    @Rule
    @JvmField
    val activityTestRule = IntentsTestRule(FirstActivity::class.java)

    @Test
    fun handleFailedStartActivityResult() {
        val result = Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null)
        intending(
            hasExtras(
                allOf(
                    hasEntry(equalTo(Constants.EXTRA_ID), equalTo(1)),
                    hasEntry(equalTo(Constants.EXTRA_NAME), equalTo("Name 1"))
                )
            )
        ).respondWith(result)
        onView(withId(R.id.buttonFirst)).perform(click())
        onView(withId(R.id.textFirst)).check(matches(withText("Cancel")))
    }
}