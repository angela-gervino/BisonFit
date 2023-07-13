package funkyflamingos.bisonfit.logic;

import java.time.Clock;

public interface IGymHoursHandler {
    String getGymSchedule();

    String getTimeUntilOpenOrClose();

    String getTimeUntilOpenOrCloseHelper(Clock clock);
}
