package com.example.twister

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.twister", appContext.packageName)

        onView(withId(R.id.emailInputField))
            .perform(clearText())
            .perform(typeText("kristian.spaqaj@gmail.com"));
        onView(withId(R.id.passwordInputField))
            .perform(clearText())
            .perform(typeText("kristian"))
            .perform(closeSoftKeyboard());
        onView(withId(R.id.button_sign_in))
            .perform(click());
        pause(1000);
        onView(withId(R.id.recycler_view))
            .check(matches(isDisplayed()));

    }
    private fun pause(value: Long){
       try {
           Thread.sleep(value)
       } catch(e: InterruptedException){
            e.printStackTrace()
       }
    }
}