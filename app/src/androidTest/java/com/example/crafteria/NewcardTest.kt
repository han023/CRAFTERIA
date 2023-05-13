package com.example.crafteria

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class NewcardTest{

    @get:Rule
    val activityScenarioRule = activityScenarioRule<Newcard>()

    @Test
    fun testemailpassword(){

        onView(withId(R.id.cvv)).perform(typeText("222"))

        onView(withId(R.id.cvv)).check(matches(withText("222")))
    }


}