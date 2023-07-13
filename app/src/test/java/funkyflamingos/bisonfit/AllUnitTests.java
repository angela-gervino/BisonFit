package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.GymHoursHandlerTest;
import funkyflamingos.bisonfit.logic.WorkoutHandlerTest;
import funkyflamingos.bisonfit.logic.UserRegistrationTest;
import funkyflamingos.bisonfit.logic.WaterHandlerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GymHoursHandlerTest.class,
        WaterHandlerTest.class,
        WorkoutHandlerTest.class,
        UserRegistrationTest.class
})

public class AllUnitTests {
}
