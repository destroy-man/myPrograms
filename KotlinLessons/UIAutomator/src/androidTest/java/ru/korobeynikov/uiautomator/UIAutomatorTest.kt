package ru.korobeynikov.uiautomator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UIAutomatorTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun testButtonInteractionAndTextInput() {
        val button = device.findObject(UiSelector().text("Click me"))
        button.click()
        val textField = device.findObject(By.res("editText"))
        textField.text = "Hello, UI Automator!"
        assertEquals("Hello, UI Automator!", textField.text)
    }

    @Test
    fun testScreenNavigation() {
        val nextButton = device.findObject(UiSelector().text("Next"))
        nextButton.click()
        val newScreenTitle = device.findObject(UiSelector().text("New Screen"))
        assertTrue(newScreenTitle.exists())
    }
}