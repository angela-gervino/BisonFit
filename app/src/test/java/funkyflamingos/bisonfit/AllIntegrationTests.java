package funkyflamingos.bisonfit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import funkyflamingos.bisonfit.logic.GymHoursHandlerIT;
import funkyflamingos.bisonfit.logic.UserRegistrationIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GymHoursHandlerIT.class,
        UserRegistrationIT.class
})

public class AllIntegrationTests {
}

