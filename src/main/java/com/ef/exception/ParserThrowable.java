package com.ef.exception;

/**
 *
 * @author Abed
 */
public class ParserThrowable extends Throwable {

    ParserThrowable(String string) {
        super(string);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        StackTraceElement[] ste = {new StackTraceElement("", "", "", 0)};
        this.setStackTrace(ste);
        return this;
    }
}
