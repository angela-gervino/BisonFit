package funkyflamingos.bisonfit.persistence;

import java.time.LocalDate;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;

public interface IGymHoursPersistence {

    List<GymHours> getNextWeekHours(LocalDate today);
    GymHours getHoursByID(int dayID);
}
