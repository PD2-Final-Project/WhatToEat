package com.whattoeat.model.api;


import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.whattoeat.model.api.InvalidTimeFormatException;

/**
 * @author hding4915
 * <p>
 *     The class is used to get the current time.
 *     Which has the precision like 2024/1/1 15:30. <br>
 *     And also check whether the time is greater than the time with the value time span you set.
 *     The default time span is 2.0 hours.
 * </p>
 * */
public class TimeSplit {
    private final double timeSpan;
    private LocalDateTime localDateTime;

    /**
     * Constructs a TimeSplit instance with the default time span of 2.0 hours.
     */
    public TimeSplit() {
        this(2.0);
    }

    /**
     * Constructs a TimeSplit instance with the specified time span.
     * @param timeSpan the time span in hours
     */
    public TimeSplit(double timeSpan) {
        this.timeSpan = timeSpan;
    }

    /**
     * @return Type of double. Return the time span you set before.
     * */
    public double getTimeSpan() {
        return timeSpan;
    }

    /**
     * @return Type of String for the current time with the format like 2024/1/1 15:30.
     * */
    public String getNowTime() {
        localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        return String.format("%d/%d/%d %d:%d", year, month, day, hour, minute);
    }

    /**
     * <p>
     *     Check whether the time is greater than the time with the value timeSpan you set.
     * </p>
     * @param time - The time of the format like 2024/1/1 15:30.
     * @return Type of boolean.
     * @throws InvalidTimeFormatException if the time format is inconsistent.
     * */
    public boolean checkTimeInterval(String time) throws InvalidTimeFormatException {
        getNowTime();
        int now_year = localDateTime.getYear();
        int now_month = localDateTime.getMonthValue();
        int now_day = localDateTime.getDayOfMonth();
        int now_hour = localDateTime.getHour();
        int now_minute = localDateTime.getMinute();
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)/(\\d+)\\s+(\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(time);

        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));
            return (year - now_year == 0) && (month - now_month == 0) && (day - now_day == 0)
                    && !(Math.abs((hour + (double) minute / 60) - (now_hour + (double) now_minute / 60)) > timeSpan);
        } else {
            throw new InvalidTimeFormatException("The provided time format is inconsistent.");
        }
    }

}
