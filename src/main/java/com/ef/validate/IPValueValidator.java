package com.ef.validate;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.ef.exception.IncorrectInputFormatException;
import java.util.regex.Pattern;

/**
 *
 * @author Abed
 */
public class IPValueValidator implements IValueValidator<String> {

    @Override
    public void validate(String string, String t) throws ParameterException {
        Pattern pattern = Pattern.compile(
                "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        boolean result = pattern.matcher(t).matches();
        if (!result) {
            throw new IncorrectInputFormatException("Input is not a valid IP address.");
        }
    }

}
