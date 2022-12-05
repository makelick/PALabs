package com.example.lab3

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun add10_000Records() {
        for (i in 1..10_000) {

            onView(withId(R.id.edit_text_key))
                .perform(replaceText("$i"))
            onView(withId(R.id.edit_text_data))
                .perform(replaceText("${getRandomString()}$i"))
                .perform(ViewActions.closeSoftKeyboard())
            onView(withId(R.id.button))
                .perform(click())
        }
    }

    @Test
    fun search10Records() {
        for (i in 1..10) {
            onView(withId(R.id.edit_text_key))
                .perform(replaceText("${(1..10_000).random()}"))
                .perform(ViewActions.closeSoftKeyboard())
            onView(withId(R.id.option_search))
                .perform(click())
            onView(withId(R.id.button))
                .perform(click())
        }
    }

    private fun getRandomString() : String {
        var str = ""
        for (i in 0..5) str += ('a'..'z').random()
        return str
    }
}