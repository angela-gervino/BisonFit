package funkyflamingos.bisonfit.logic;

import java.time.Clock;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.TimeUntilOpenOrClose;

public interface IGymHoursHandler {
    List<GymHours> getGymSchedule();

    TimeUntilOpenOrClose getTimeUntilOpenOrClose();

    TimeUntilOpenOrClose getTimeUntilOpenOrCloseHelper(Clock clock);

}
