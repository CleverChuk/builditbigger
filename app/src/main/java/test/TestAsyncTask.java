package test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * test
 * Created by chuk on 5/11/18,at 13:17.
 */
@RunWith(AndroidJUnit4.class)
public class TestAsyncTask {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(
            MainActivity.class);

    private CountingIdlingResource countingIdlingResource = rule.getActivity().getCountingIdlingResource();

    @Before
    public void registerCountingIdlingResource() {
        Espresso.registerIdlingResources(countingIdlingResource);
    }

    @Test
    public void testTask() {
        onView(withText("Tell Joke")).perform(click());
        onView(withId(R.id.jokes_tv)).check(
                matches(
                        not(
                                withText("")
                        )
                )
        );
    }


    @After
    public void unRegisterCountingIdlingResource() {
        Espresso.unregisterIdlingResources(countingIdlingResource);
    }

}
