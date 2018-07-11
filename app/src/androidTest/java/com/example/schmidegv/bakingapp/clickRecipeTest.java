package com.example.schmidegv.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.schmidegv.bakingapp.activities.MainActivity;
import com.example.schmidegv.bakingapp.networking.OkHttpProvider;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by schmidegv on 2018. 07. 11..
 */

@RunWith(AndroidJUnit4.class)
public class clickRecipeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickItem() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(
                "okhttp", OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps_fragment)).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
