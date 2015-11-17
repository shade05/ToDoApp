package com.codepath.courses.todoapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.codepath.courses.todoapp.domain.ToDoItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by deepaks on 11/9/15.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> withAdaptedData(final String title) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText(title + " does not exists");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    final ToDoItem toDoItem = (ToDoItem) adapter.getItem(i);
                    System.out.println("Title iteration " + toDoItem.getTitle());
                    if (toDoItem.getTitle().equals(title)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Test
    public void executeActivity() {
        String title = "Testing Item: " + System.currentTimeMillis();
        onView(withId(R.id.editText)).perform(click()).perform(typeText(title));
        onView(withId(R.id.editText)).check(matches(withText(title)));
        onView(allOf(ViewMatchers.withEffectiveVisibility(Visibility.VISIBLE), withId(R.id.addButton))).
                perform(click());

        onView(withId(R.id.listView)).check(ViewAssertions.matches(withAdaptedData(title)));
        /*onData(instanceOf(ToDoItem.class))
                .inAdapterView(allOf(withId(R.id.listView), isDisplayed()))
                .atPosition(2)
                .check(matches(isDisplayed()));*/
    }
}
