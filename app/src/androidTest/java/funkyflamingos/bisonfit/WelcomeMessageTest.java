package funkyflamingos.bisonfit;

//import android.support.test.espresso.assertion.ViewAssertions;
//import android.support.test.filters.LargeTest;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

//import comp3350.srsys.application.Services;
//import comp3350.srsys.objects.Course;
//import comp3350.srsys.persistence.CoursePersistence;
//import comp3350.srsys.presentation.HomeActivity;
import funkyflamingos.bisonfit.ui.HomePageActivity;
import funkyflamingos.bisonfit.ui.WelcomeActivity;

//import static android.support.test.espresso.Espresso.closeSoftKeyboard;
//import static android.support.test.espresso.Espresso.onData;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.pressBack;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withParent;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WelcomeMessageTest {
    @Rule
    public ActivityScenarioRule<WelcomeActivity> activityRule = new ActivityScenarioRule<>(WelcomeActivity.class);


//    @Before
//    public void setupDatabase(){
//        // clear 4050 from the database
//        CoursePersistence coursePersist = Services.getCoursePersistence();
//        List<Course> courses = coursePersist.getCourseSequential();
//        for (Course c: courses)
//            if (c.getCourseID().equals("4050"))
//                coursePersist.deleteCourse(c);
//    }


    @Test
    public void createCourse() {

        // enter username on first run
        onView(withId(R.id.nameEditText)).perform(click());
        onView(withId(R.id.nameEditText)).perform(typeText("user1"));
        onView(withId(R.id.btnStartWelcomeActivity)).perform(click());

        // verify that username is used in welcome message
        onView(withId(R.id.lblGreetings)).check(matches(withText("Hi user1!")));

//        // add the new course
//        onView(withId(R.id.editCourseID)).perform(typeText("4050"));
//        onView(withId(R.id.editCourseName)).perform(typeText("Project Management"));
//        onView(withId(R.id.buttonCourseCreate)).perform(click());
//        closeSoftKeyboard();

        // verify that it was added
//        pressBack();
//        onView(withId(R.id.buttonCourses)).perform(click());
//        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
//        onView(withId(R.id.editCourseName)).check(matches(withText("Project Management")));
    }
}


