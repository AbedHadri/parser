package com.ef.util.output;

import com.ef.model.LogEntry;
import de.vandermeer.asciitable.AsciiTable;
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

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow("Count", "Access IP", "Request Header", "Response", "User Agent");

        int count = 0;

        for (LogEntry logEntry : logEntries) {
            count = count + 1;
            at.addRule();
            at.addRow(count,
                    logEntry.getAccessIp(),
                    logEntry.getRequestHeader(),
                    logEntry.getResponseCode(),
                    logEntry.getUserAgent());

        }
        System.out.println(at.render() + "\n");
        System.out.println("total rows found : " + count + "\n");

    }

}
