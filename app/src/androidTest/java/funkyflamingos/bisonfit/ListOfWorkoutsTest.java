package funkyflamingos.bisonfit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;
import funkyflamingos.bisonfit.ui.WelcomeActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListOfWorkoutsTest {
    @Rule
    public ActivityScenarioRule<WelcomeActivity> activityRule = new ActivityScenarioRule<>(WelcomeActivity.class);

    @Before
    public void setupDatabase() {
        // clear username from the database
        IUserRegistrationPersistence userRegistrationPersistence = Services.getUserRegistrationPersistence();
        userRegistrationPersistence.clearTable();

        // delete all current workouts
        IWorkoutPersistence workoutPersistence = Services.getWorkoutsPersistence();
        workoutPersistence.clear();
    }

    @Test
    public void gymScheduleTest() {

        // we need to close and reopen the app because otherwise the test is flaky since there
        // is a race condition between the @Before and the @Rule so sometimes the username will
        // be accessed before it is deleted in the @Before so we never see the sign in page
        activityRule.getScenario().close();
        ActivityScenario.launch(WelcomeActivity.class);

        // enter username on first run
        onView(withId(R.id.nameEditText)).perform(click());
        onView(withId(R.id.nameEditText)).perform(typeText("user1"));
        onView(withId(R.id.btnStartWelcomeActivity)).perform(click());

        // add a workouts
        onView(withId(R.id.addWorkoutButton)).perform(click());
        onView(withId(R.id.new_workout_name)).perform(typeText("workout1"));
        onView(withText("CREATE WORKOUT")).perform(click());
        onView(withId(R.id.addWorkoutButton)).perform(click());
        onView(withId(R.id.new_workout_name)).perform(typeText("workout2"));
        onView(withText("CREATE WORKOUT")).perform(click());

        // check for list of workouts
        onView(withText("workout1")).check(matches(isDisplayed()));
        onView(withText("workout2")).check(matches(isDisplayed()));
    }
}
