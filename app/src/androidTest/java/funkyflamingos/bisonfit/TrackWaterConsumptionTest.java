package funkyflamingos.bisonfit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.ui.WelcomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import java.time.LocalDate;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TrackWaterConsumptionTest {
    @Rule
    public ActivityScenarioRule<WelcomeActivity> activityRule = new ActivityScenarioRule<>(WelcomeActivity.class);

    IWaterTrackerPersistence waterTrackerPersistence;
    LocalDate today;

    @Before
    public void setupDatabase() {
        // clear username from the database
        IUserRegistrationPersistence userRegistrationPersistence = Services.getUserRegistrationPersistence();
        userRegistrationPersistence.clear();

        // reset water tracker
        today = LocalDate.now();
        waterTrackerPersistence = Services.getWaterTrackPersistence();
        waterTrackerPersistence.clear(today);
    }

    @Test
    public void trackWaterConsumptionTest() {

        LocalDate today = LocalDate.now();
        IWaterTrackerPersistence waterTrackerPersistence = Services.getWaterTrackPersistence();

        // we need to close and reopen the app because otherwise the test is flaky since there
        // is a race condition between the @Before and the @Rule so sometimes the username will
        // be accessed before it is deleted in the @Before so we never see the sign in page
        activityRule.getScenario().close();
        ActivityScenario.launch(WelcomeActivity.class);

        // enter username on first run
        onView(withId(R.id.nameEditText)).perform(click());
        onView(withId(R.id.nameEditText)).perform(typeText("user1"));
        onView(withId(R.id.btnStartWelcomeActivity)).perform(click());

        // Given the nature of this feature we were unable to find a way to test it through the ui.
        // This is because  the progress bar uses an animation which can't be dynamically tested
        // with Espresso. Instead we have opted to test that the UI is at least making changes in
        // the database.
        assertEquals(0, waterTrackerPersistence.getProgress(today));

        onView(withId(R.id.circularProgressButton)).perform(click());

        assertEquals(1, waterTrackerPersistence.getProgress(today));
    }
}
