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

class RegistarasasellerTest{

    @get:Rule
    val activityScenarioRule = activityScenarioRule<Registarasaseller>()

    @Test
    fun testemailpassword(){

        onView(withId(R.id.registarasaselleremail)).perform(typeText("example@gmail.com"))
        onView(withId(R.id.registarasasellerpassword)).perform(typeText("123456"))
        onView(withId(R.id.registarasasellerconpassword)).perform(typeText("123456"))

        onView(withId(R.id.registarasaselleremail)).check(matches(withText("example@gmail.com")))
        onView(withId(R.id.registarasasellerconpassword)).check(matches(withText("123456")))
        onView(withId(R.id.registarasasellerpassword)).check(matches(withText("123456")))
    }


}