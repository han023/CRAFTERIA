package com.example.crafteria

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test


class AddressFragmentTest{
    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPostalCodeInput() {

        // Select the tab that contains the AddressFragment
        onView(withId(R.id.bottomNavigationView)).perform(click())
        onView(withId(R.id.address)).check(matches(isDisplayed()))

        // Enter a postal code
//        onView(withId(R.id.total)).perform(replaceText("123"))


        // Check if the entered postal code is correct
        onView(withId(R.id.total)).check(matches(withText("update")))
    }


}