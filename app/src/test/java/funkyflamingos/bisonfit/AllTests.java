package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.GymStatusHandlerTest;
import funkyflamingos.bisonfit.logic.RoutineHandlerTest;
import funkyflamingos.bisonfit.logic.WaterHandlerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GymStatusHandlerTest.class,
        WaterHandlerTest.class,
        RoutineHandlerTest.class,
        UserRegistration.class
})

public class AllTests {
}
