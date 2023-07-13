package funkyflamingos.bisonfit.logic;

import static funkyflamingos.bisonfit.application.Constants.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;

import java.time.*;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.Hours;
import funkyflamingos.bisonfit.exceptions.GymHoursNotConsecutiveException;
import funkyflamingos.bisonfit.exceptions.GymHoursNotStartTodayException;
import funkyflamingos.bisonfit.exceptions.GymHoursOverAWeekException;
import funkyflamingos.bisonfit.exceptions.HoursEmptyException;
import funkyflamingos.bisonfit.exceptions.HoursOrderException;
import funkyflamingos.bisonfit.exceptions.NullGymHoursException;
import funkyflamingos.bisonfit.exceptions.NullHoursException;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

public class GymHoursHandler implements IGymHoursHandler {
    private IGymHoursPersistence persistence;

    public GymHoursHandler(IGymHoursPersistence persistence) {
        this.persistence = persistence;
    }

    public GymHoursHandler() {
        this.persistence = Services.getGymHoursPersistence();
    }

    @Override
    public String getGymSchedule() {
        Clock clock = Clock.systemDefaultZone();
        LocalDate currentDate = LocalDate.now(clock);
        int currentDayOfWeek = getDayOfWeek(currentDate);

        List<GymHours> nextWeekHours = persistence.getNextWeekHours(LocalDate.now(clock));
        validateGymHours(nextWeekHours, currentDate);

        String printableSchedule = "";

        for (GymHours gymHours : nextWeekHours) {
            int dayID = gymHours.getDayID();
            printableSchedule += dayIDToString(dayID);
            if (dayID == currentDayOfWeek)
                printableSchedule += " **Today**";
            printableSchedule += "\n";

            List<Hours> hoursList = gymHours.getHours();
            if (hoursList != null) {
                for (Hours hours : hoursList) {
                    printableSchedule += "\t";
                    printableSchedule += getClockFormattedTime(hours.getOpening(), false);
                    printableSchedule += " - ";
                    printableSchedule += getClockFormattedTime(hours.getClosing(), true);
                    printableSchedule += "\n";
                }
            } else {
                printableSchedule += "\t";
                printableSchedule += "Closed\n";
            }
            printableSchedule += "\n";
        }
        return printableSchedule;
    }

    @Override
    public String getTimeUntilOpenOrClose() {
        return getTimeUntilOpenOrCloseHelper(Clock.systemDefaultZone());
    }

    /**
     * Helper method is public so we can pass it a clock for testing.
     */
    @Override
    public String getTimeUntilOpenOrCloseHelper(Clock clock) {
        LocalTime currentTime = LocalTime.now(clock);
        LocalDate currentDate = LocalDate.now(clock);
        List<GymHours> nextWeekHours = persistence.getNextWeekHours(LocalDate.now(clock));
        validateGymHours(nextWeekHours, currentDate);

        Hours currentHours = getCurrentlyOpenHours(nextWeekHours, currentTime);

        if (currentHours != null) // is currently open
            return getNextClosingString(currentHours, currentTime, nextWeekHours);
        else
            return getNextOpeningString(currentDate, currentTime, nextWeekHours);
    }

    /**
     * static helper methods
     */
    // returns the day after dayOfWeek where Monday = 1, Tuesday = 2, etc
    public static int getNextDayOfWeek(int dayOfWeek) {
        dayOfWeek++;
        dayOfWeek = (dayOfWeek - 1) % DAYS_PER_WEEK + 1;
        return dayOfWeek;
    }

    // returns a formatted string that says when the next time the gym is open
    private static String getNextOpeningString(LocalDate currentDate, LocalTime currentTime, List<GymHours> nextWeekHours) {
        Duration timeUntilClosing = null;
        int currentDayOfWeek = getDayOfWeek(currentDate);
        String nextOpenDay = null;
        boolean found = false;

        // traverse gymHours for next 7 days
        for (int i = 0; i < nextWeekHours.size() && !found; i++) {
            GymHours gymHours = nextWeekHours.get(i);
            List<Hours> hoursList = gymHours.getHours();

            if (hoursList != null) { // not closed all day
                for (int j = 0; j < hoursList.size() && !found; j++) {
                    Hours hours = hoursList.get(j);
                    boolean isToday = gymHours.getDayID() == currentDayOfWeek;

                    // is later today or later in the week
                    if (hours.getOpening().isAfter(currentTime) || !isToday) {
                        if (isToday) {
                            timeUntilClosing = getDurationBetween(currentTime, hours.getOpening());
                        } else if (isTomorrow(currentDayOfWeek, gymHours.getDayID())) {
                            timeUntilClosing = getDurationBetween(currentTime, LocalTime.MIDNIGHT)
                                    .plus(Duration.between(LocalTime.MIN, hours.getOpening()));
                        } else {
                            // if the gym opens after tomorrow we just
                            // show the next day the gym is open
                            nextOpenDay = dayIDToString(gymHours.getDayID());
                        }
                        found = true;
                    }
                }
            }
        }

        // return formatted string depending on when the gym is open next
        if (timeUntilClosing != null) {
            return getFormattedTime(timeUntilClosing.toString()) + " Until Opening";
        } else if (nextOpenDay != null) {
            return "Closed Till " + nextOpenDay;
        } else {
            return "Closed All Week";
        }
    }

    // determines if other is one day after today where Monday = 1, Tuesday = 2, etc
    private static boolean isTomorrow(int today, int other) {
        int tomorrow = getNextDayOfWeek(today);
        return tomorrow == other;
    }

    // returns a formatted string that says when the next time the is closed
    private static String getNextClosingString(Hours currentHours, LocalTime currentTime, List<GymHours> nextWeekHours) {
        Duration timeUntilClosing;
        List<Hours> tomorrowsHours = nextWeekHours.get(1).getHours();
        List<Hours> dayAfterTomorrowHours = nextWeekHours.get(2).getHours();

        timeUntilClosing = getDurationBetween(currentTime, currentHours.getClosing());

        boolean isOpenTillTomorrow = currentHours.getClosing().equals(LocalTime.MIDNIGHT)
                && tomorrowsHours != null
                && tomorrowsHours.get(0).getOpening().equals(LocalTime.MIDNIGHT);
        boolean isOpenTillDayAfterTomorrow = isOpenTillTomorrow
                && tomorrowsHours.get(0).getClosing().equals(LocalTime.MIDNIGHT)
                && dayAfterTomorrowHours != null
                && dayAfterTomorrowHours.get(0).getOpening().equals(LocalTime.MIDNIGHT);

        if (isOpenTillTomorrow)
            if (!isOpenTillDayAfterTomorrow)
                // add duration of tomorrow's first hours
                timeUntilClosing = timeUntilClosing.plus(getDurationBetween(LocalTime.MIDNIGHT, tomorrowsHours.get(0).getClosing()));
            else
                // not closing today or tomorrow so we just show the next day that the gym closes
                return getClosingDayString(nextWeekHours);

        return getFormattedTime(timeUntilClosing.toString()) + " Until Closing";
    }

    // returns message with the next day of the week the gym is closed
    private static String getClosingDayString(List<GymHours> weekHours) {
        GymHours curr;
        GymHours prev = weekHours.get(1);

        for (int i = 2; i < DAYS_PER_WEEK; i++) {
            curr = weekHours.get(i);
            if (curr.getHours() == null
                    || !(prev.getHours().get(0).getClosing().equals(LocalTime.MIDNIGHT)
                    && curr.getHours().get(0).getOpening().equals(LocalTime.MIDNIGHT))) {
                return "Open Till " + dayIDToString(prev.getDayID());
            }
            prev = curr;
        }
        return "Open All Week";
    }

    private static Duration getDurationBetween(LocalTime start, LocalTime end) {
        Duration timeUntilClosing;
        if (end.equals(LocalTime.MIDNIGHT)) {
            timeUntilClosing = Duration.between(start, LocalTime.MAX);
            timeUntilClosing = timeUntilClosing.plusSeconds(1);
        } else
            timeUntilClosing = Duration.between(start, end);
        return timeUntilClosing;
    }

    // return hours if current time is after opening and before closing
    private static Hours getCurrentlyOpenHours(List<GymHours> nextWeekHours, LocalTime currentTime) {
        List<Hours> todayHours = nextWeekHours.get(0).getHours();
        if (todayHours != null)
            for (Hours hours : todayHours)
                if (currentTime.isAfter(hours.getOpening())
                        && (currentTime.isBefore(hours.getClosing())
                        || hours.getClosing().equals(LocalTime.MIDNIGHT)))
                    return hours;
        return null;
    }

    // converts Duration.toString() to human readable
    private static String getFormattedTime(String durationToString) {
        String output = durationToString.substring(2);

        if (output.contains("M")) {
            output = output.replaceAll("M[0-9.S]*", "m").toLowerCase().replaceAll("h", "h ");
        } else {
            if (output.contains("H")) {
                output = output.replaceAll("H[0-9.S]*", "h 0m");
            } else {
                output = "<1 Minute";
            }
        }
        return output;
    }

    // converts LocalTime to 24 hour human readable: hh:mm
    private static String getClockFormattedTime(LocalTime time, boolean isClosingTime) {
        String formattedTime = "";
        String hour = String.valueOf(time.getHour());

        if (isClosingTime && hour.equals("0")) {
            formattedTime += "24";
        } else {
            if (hour.length() < 2)
                formattedTime += " ";
            formattedTime += time.getHour();
        }

        formattedTime += ":";
        String minute = String.valueOf(time.getMinute());
        if (minute.length() < 2)
            formattedTime += "0";
        formattedTime += minute;
        return formattedTime;
    }


    // Converts 1 to "Monday", 2 to "Tuesday", etc
    private static String dayIDToString(int dayID) {
        String dayOfWeek = DayOfWeek.of(dayID).toString();
        dayOfWeek = dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
        return dayOfWeek;
    }


    private static void validateGymHours(List<GymHours> nextWeekHours, LocalDate today) {

        // should be truthy
        if (nextWeekHours == null)
            throw new NullGymHoursException("GymHours should not be null.");

        // size should be one week
        if (nextWeekHours.size() != DAYS_PER_WEEK)
            throw new GymHoursOverAWeekException("GymHours should be exactly 7 days long.");

        int todayID = getDayOfWeek(today);
        int firstID = nextWeekHours.get(0).getDayID();

        // first day should be today
        if (todayID != firstID)
            throw new GymHoursNotStartTodayException("First day ID does not match today.");

        // validate hours
        for (GymHours gymHours : nextWeekHours) {

            // should be truthy
            if (gymHours == null)
                throw new NullGymHoursException("GymHours should not be null.");

            validateHours(gymHours.getHours());
        }

        // days should be consecutive
        int currID = getNextDayOfWeek(todayID);

        for (int i = 1; i < nextWeekHours.size(); i++) {

            // days should be consecutive
            if (nextWeekHours.get(i).getDayID() != currID)
                throw new GymHoursNotConsecutiveException("Days should be consecutive.");

            currID = getNextDayOfWeek(currID);
        }
    }

    private static void validateHours(List<Hours> hours) {

        // hours can be null
        if (hours != null) {

            // hours should not be empty
            if (hours.size() == 0)
                throw new HoursEmptyException("Hours should not be empty.");

            // all opening and closing times should be consecutive and not equal
            // except for the special case that the first opening and
            // last closing times can be both be zero
            LocalTime prev = LocalTime.MIN;
            LocalTime curr = null;
            for (int i = 0; i < hours.size(); i++) {

                // check list
                if (hours.get(i) == null)
                    throw new NullHoursException("Hours should not be null.");

                // check opening
                curr = hours.get(i).getOpening();
                if (curr == null)
                    throw new NullHoursException("Opening should not be null.");

                if (i != 0 && curr.compareTo(prev) <= 0)
                    throw new HoursOrderException("Opening should be after last closing.");

                prev = curr;

                // check closing
                curr = hours.get(i).getClosing();
                if (curr == null)
                    throw new NullHoursException("Closing should not be null.");

                if (i == hours.size() - 1 && curr.equals(LocalTime.MIDNIGHT))
                    continue;
                if (curr.compareTo(prev) <= 0)
                    throw new HoursOrderException("Closing should be after last opening.");

                prev = curr;
            }
        }
    }
}
