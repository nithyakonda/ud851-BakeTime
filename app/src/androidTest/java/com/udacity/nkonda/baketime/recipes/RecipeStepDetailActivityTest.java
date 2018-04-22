package com.udacity.nkonda.baketime.recipes;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailActivity;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeStepDetailActivityTest {
    private static final int RECIPE_ID = 4;
    private static final int STEP_ID_0 = 0;
    private static final int STEP_ID_1 = 1;
    private static final int STEP_ID_12 = 12;
    private static final String STEP_ID_0_DESC = "Recipe Introduction";
    private static final String STEP_ID_1_DESC = "1. Preheat the oven to 350°F. Grease the bottom of a 9-inch round springform pan with butter. ";
    private static final String STEP_ID_2_DESC = "2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. Stir in the melted butter and 1 "
        + "teaspoon of vanilla extract until uniform. ";

    @Rule
    public ActivityTestRule<RecipeStepDetailActivity> mActivityTestRule = new ActivityTestRule<RecipeStepDetailActivity>(RecipeStepDetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RecipeStepDetailActivity.class);
            result.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, RECIPE_ID);
            result.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, STEP_ID_1);
            return result;
        }
    };

    @Test
    public void onClickStep_OpensCorrectStepDetail() {
        checkIfDescMatches(STEP_ID_1_DESC);
    }

    @Test
    public void onClickNext_OpensNextStepDetail() {
        onView(withId(R.id.btn_next)).perform(click());
        checkIfDescMatches(STEP_ID_2_DESC);
    }

    @Test
    public void onClickPrev_OpensPrevStepDetail() {
        onView(withId(R.id.btn_prev)).perform(click());
        checkIfDescMatches(STEP_ID_0_DESC);
    }

    @Test
    public void isPrevButtonDisabled_ForStep0() {
        mActivityTestRule.finishActivity();
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RecipeStepDetailActivity.class);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, RECIPE_ID);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, STEP_ID_0);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.btn_prev)).check(matches(not(isEnabled())));
    }

    @Test
    public void isNextButtonDisabled_ForStep12() {
        mActivityTestRule.finishActivity();
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RecipeStepDetailActivity.class);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, RECIPE_ID);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, STEP_ID_12);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.btn_next)).check(matches(not(isEnabled())));
    }

    @Test
    public void isNoVideoDisplayed_ForStep1() {
        onView(withId(R.id.no_video_background)).check(matches(isDisplayed()));
    }

    @Test
    public void isVideoDisplayed_ForStep0() {
        mActivityTestRule.finishActivity();
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RecipeStepDetailActivity.class);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, RECIPE_ID);
        intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, STEP_ID_0);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.exo_media)).check(matches(isDisplayed()));
    }

    private void checkIfDescMatches(String stepDesc) {
        ViewInteraction textView = onView(withId(R.id.tv_desc));
        textView.check(matches(withText(stepDesc)));
    }
}
