package com.example.crafteria

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.crafteria.homefragment.CartFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartFragmentTest{

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPostalCodeInput() {

        // Select the tab that contains the AddressFragment
        onView(withId(R.id.bottomNavigationView)).perform(ViewActions.click())
        onView(withId(R.id.cart)).check(matches(isDisplayed()))

        onView(withId(R.id.total)).check(matches(withText("update")))
    }


}