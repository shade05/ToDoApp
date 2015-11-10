package com.codepath.courses.todoapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by deepaks on 11/9/15.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void executeActivity() {
        Espresso.onView(AllOf.allOf(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), ViewMatchers.withId(R.id.addButton))).
                perform(ViewActions.click());


    }
}
