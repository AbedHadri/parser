package com.ef.util.output;

import com.ef.model.LogEntry;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author Abed
 */
public class TablePrint {

    public static void printLogEntryTable(List<LogEntry> logEntries) {
        drawFullLine();
        System.out.printf("%6s %-17s %-17s %-10s %-50s%n", "Count", "Access IP", "Request Header", "Response", "User Agent");
        drawFullLine();

        int count = 0;
        StringBuilder sbOutput = new StringBuilder();

        for (LogEntry logEntry : logEntries) {
            count = count + 1;

            sbOutput.append(String.format(" %-6d%-17s %-17s %-10d %-50s%n%n",
                    count, logEntry.getAccessIp(),
                    WordUtils.wrap(logEntry.getRequestHeader(), 20, "\n" + StringUtils.repeat(" ", 24), true),
                    logEntry.getResponseCode(),
                    WordUtils.wrap(logEntry.getUserAgent(), 20, "\n" + StringUtils.repeat(" ", 54), true)));

        }
        System.out.println(sbOutput.toString());
        drawFullLine();
        System.out.println("total found rows : " + count + "\n");

    }

    private static void drawFullLine() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
