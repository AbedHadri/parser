package com.ef.util.converter;

import com.ef.exception.DurationNotKnowException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Abed
 */
public class DateUtils {

    public static final String DAILY = "daily";
    public static final String HOURLY = "hourly";

    public static Date toDate(String stringToConvert) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date resultDate = null;
        try {
            resultDate = df.parse(stringToConvert);
        } catch (ParseException e) {
            System.err.println("string " + stringToConvert + " failed to convert to date.");
        }
        return resultDate;
    }

    public static Date getDateAfterDuration(Date startDate, String duration) throws DurationNotKnowException {

        long resultDateInLong = 0;

        if (duration.equals(DAILY)) {
            resultDateInLong = startDate.getTime() + (24 * 60 * 60 * 1000);
        } else if (duration.equals(HOURLY)) {
            resultDateInLong = startDate.getTime() + (60 * 60 * 1000);
        } else {
            throw new DurationNotKnowException();
        }

        return new Date(resultDateInLong);
    }

}
