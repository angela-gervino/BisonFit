package funkyflamingos.bisonfit.logic;

import java.time.*;
import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymStatusPersistence;

public class HoursHandler {
    private IGymStatusPersistence database;

    public HoursHandler(IGymStatusPersistence database)
    {
        this.database = database;
    }

    public String getGymStatus(Clock clock) {
        String result = " Until Closing";
        LocalTime currentTime = LocalTime.now(clock);
        LocalDate currentDay = LocalDate.now(clock);
        DayOfWeek day = currentDay.getDayOfWeek();
        int dayOfWeekTodayAsInt = day.getValue();
        int dayOfWeekTomorrowAsInt = dayOfWeekTodayAsInt == 7 ? 1 : dayOfWeekTodayAsInt + 1;

        GymHours todaysHours = database.getHoursByID(dayOfWeekTodayAsInt);
        GymHours tomorrowsHours = database.getHoursByID(dayOfWeekTomorrowAsInt);

        LocalTime closingTime = todaysHours.getClosing();
        LocalTime openTime = tomorrowsHours.getOpening();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime endOfDay = LocalTime.MAX;

        Duration currToOpening = Duration.between(currentTime, openTime);
        Duration currToClosing = Duration.between(currentTime, closingTime);
        Duration duration = currToClosing;

        if (currToOpening.isZero()) {
            duration = Duration.between(openTime, closingTime);
        }
        else if (currToClosing.isZero()) {
            duration = Duration.between(closingTime, endOfDay).plus(Duration.between(midnight, openTime));
            duration = duration.plusSeconds(1);
            result = " Until Opening";
        }
        else if(!currToClosing.isNegative() && !currToOpening.isNegative()) { // past midnight
            duration = currToOpening;
            result = " Until Opening";
        }
        else if (currToClosing.isNegative() && currToOpening.isNegative()) { // before midnight
            duration = Duration.between(midnight, openTime).plus(Duration.between(currentTime, endOfDay));
            duration = duration.plusSeconds(1);
            result = " Until Opening";
        }

        return getFormattedTime(duration.toString()) + result;
    }

    private String getFormattedTime(String durationToString) {
        String output = durationToString.substring(2)
                .replaceAll("[.][1-9]+S", "s")
                .replaceAll("H", "h ")
                .replaceAll("M", "m ")
                .replaceAll("S", "s");

        if (!output.contains("h"))
        {
            output = "0h " + output;
        }

        if (!output.contains("m"))
        {
            output = output.replaceAll("h", "h 0m");
        }

        if (!output.contains("s"))
        {
            output = output + "0s";
        }
        return output;
    }
}
