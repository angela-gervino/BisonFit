package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.GymHoursHandlerIT;
import funkyflamingos.bisonfit.logic.WorkoutHandlerIT;
import funkyflamingos.bisonfit.logic.UserRegistrationIT;
import funkyflamingos.bisonfit.logic.WaterHandlerIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GymHoursHandlerIT.class,
        WorkoutHandlerIT.class,
        UserRegistrationIT.class,
        WaterHandlerIT.class
})

public class AllIntegrationTests {
}

