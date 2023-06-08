package funkyflamingos.bisonfit.persistence;

import funkyflamingos.bisonfit.dso.GymHours;

public interface IGymStatusPersistence {

    GymHours getHoursByID(int dayID);
}
