package com.zufar.submision3.view

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.zufar.submision3.R
import com.zufar.submision3.R.id.*
import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.Delay
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.timeunit.TimeUnit
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest{

    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(3000)
        onView(withId(rv_past_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_past_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_past_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
    }

    @Test
    fun testAppBehaviour(){
        Thread.sleep(1000)
        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(next_match)).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_next_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_next_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        pressBack()

        Thread.sleep(3000)
        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(favourites_match)).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_fav_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_fav_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rv_fav_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
            .check(matches(isDisplayed()))
        pressBack()
    }
}