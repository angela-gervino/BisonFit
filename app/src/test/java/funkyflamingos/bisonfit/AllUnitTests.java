package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.GymHoursHandlerTest;
import funkyflamingos.bisonfit.logic.RoutineHandlerTest;
import funkyflamingos.bisonfit.logic.WaterHandlerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GymHoursHandlerTest.class,
        WaterHandlerTest.class,
        RoutineHandlerTest.class,
        UserRegistration.class
})

public class AllUnitTests {
}
