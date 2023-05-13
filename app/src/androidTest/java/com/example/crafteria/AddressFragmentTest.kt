package com.example.crafteria

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.crafteria.homefragment.AddressFragment
import com.example.crafteria.homefragment.CartFragment
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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
        onView(withId(R.id.postal)).perform(typeText("123"), closeSoftKeyboard())
//
//        // Check if the entered postal code is correct
        onView(withId(R.id.postal)).check(matches(withText("123")))
    }


}