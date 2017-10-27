package com.ef.util.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Abed
 */
public class DateUtils {

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

}
