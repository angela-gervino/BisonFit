package funkyflamingos.bisonfit.logic;

import java.time.Clock;

public interface IGymHoursHandler {
    String getGymSchedule() throws Exception;

    String getTimeUntilOpenOrClose() throws Exception;

    String getTimeUntilOpenOrCloseHelper(Clock clock) throws Exception;
}
