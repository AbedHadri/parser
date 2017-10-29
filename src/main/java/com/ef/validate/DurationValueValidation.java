package com.ef.validate;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.ef.exception.IncorrectInputFormatException;
import com.ef.util.converter.DateUtils;

/**
 *
 * @author Abed
 */
public class DurationValueValidation implements IValueValidator<String> {

    @Override
    public void validate(String string, String t) throws ParameterException {
        if (!(t.equals(DateUtils.HOURLY) || t.equals(DateUtils.DAILY))) {
            throw new IncorrectInputFormatException("only 'daily' or 'hourly' values are accepted as duration.");
        }
    }

}
