package com.example.numberguess

import android.content.res.Resources
import android.util.Log

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {

    /*
    activityScenarioRule:取得Activity資源
    先尋找元件再調用動作or檢查

    尋找UI元件:
    onView(Matcher)
    1.onView(withId()):利用ID。
    2.onView(withText()):利用文字。

    操作UI動作:
    perform(ViewActions)
    1.perform(typeText()):輸入文字。
    2.perform(click()):點擊。
    3.perform(clearText()):清空文字。

    檢查:
    check(ViewAssertion(Matcher))
    1.check(matches(withId)):是否有這個ID
    2.check(matches(withText)):是否有這個文字
    3.check(matches(isDisplayed())):是否有顯示
    */

    val TAG = "MaterialActivityTest"
    var secret = 0
    lateinit var resources: Resources
    val guess_times = 5
    val play_rounds = 2

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
        // access resources in activity(MaterialActivity)
        activityScenarioRule.scenario.onActivity { activity ->
            secret = activity.secretNumber.secret
            resources = activity.resources

            Log.d(TAG, "Secret number: $secret")
        }

        for (number in 1..guess_times) {
            if (number != secret) {// only test for guess large and guess small case
                val message =
                    if (number < secret) resources.getString(R.string.guess_bigger)
                    else resources.getString(R.string.guess_smaller)

                // clear text -> type number -> click ok
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(typeText(number.toString()))
                onView(withId(R.id.ok_btn)).perform(click())

                // string check
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }

    @Test
    fun replay_counter_check() {
        for (round in 1..play_rounds) { // play n rounds, default: 2 rounds
            activityScenarioRule.scenario.onActivity { activity ->
                resources = activity.resources
                secret = activity.secretNumber.secret
                Log.d(TAG, "Secret number: $secret")
            }

            for (number in 1..guess_times) { // guess n times per round, default: 5 times
                if (number != secret) {
                    onView(withId(R.id.ed_number)).perform(clearText())
                    onView(withId(R.id.ed_number)).perform(typeText(number.toString()))
                    onView(withId(R.id.ok_btn)).perform(click())
                    onView(withText(resources.getString(R.string.ok))).perform(click())
                }
            }

            // press replay button to check whether the play counter is reset to zero
            // close keyboard -> press fab button to replay-> check whether the counter equals to zero
            onView(withId(R.id.ed_number)).perform(closeSoftKeyboard())
            onView(withId(R.id.fab)).perform(click())
            onView(withText(resources.getString(R.string.yes))).perform(click())
            onView(withId(R.id.counter)).check(matches(withText("0")))
        }
    }
}