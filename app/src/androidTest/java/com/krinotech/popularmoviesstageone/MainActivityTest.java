package com.krinotech.popularmoviesstageone;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final ActivityTestRule<MainActivity> testSubject =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void launch_should_see_title() {
        // Context of the app under test.
        testSubject.launchActivity(null);

        String title = "Pop Movies";

        onView(withText(title)).check(matches(isDisplayed()));
    }
}
