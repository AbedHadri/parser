package com.ef.exception;

import com.beust.jcommander.ParameterException;

/**
 *
 * @author Abed
 */
public class IncorrectInputFormatException extends ParameterException {

    public IncorrectInputFormatException(String string) {
        super(string);
    }

    public IncorrectInputFormatException(Throwable thrwbl) {
        super(thrwbl);
    }

}
