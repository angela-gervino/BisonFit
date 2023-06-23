package funkyflamingos.bisonfit.persistence;

import funkyflamingos.bisonfit.dso.GymHours;

public interface IGymHoursPersistence {

    GymHours getHoursByID(int dayID);
}
