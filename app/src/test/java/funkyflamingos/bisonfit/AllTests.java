package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.HoursHandlerTest;
import funkyflamingos.bisonfit.logic.RoutineHandlerTest;
import funkyflamingos.bisonfit.logic.WaterHandlerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        HoursHandlerTest.class,
        WaterHandlerTest.class,
        RoutineHandlerTest.class
})

public class AllTests {
}
