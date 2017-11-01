package com.ef.util.output;

import com.ef.model.IpFiltered;
import com.ef.model.LogEntry;
import de.vandermeer.asciitable.AsciiTable;
import java.util.List;

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
        System.out.println("Total rows found : " + count + "\n");

    }

    public static void printBlackListTable(List<IpFiltered> filteredList) {

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow("Count", "IP Address", "Request Count", "Reason Block");

        int count = 0;
        for (IpFiltered filtered : filteredList) {
            count = count + 1;
            at.addRule();
            at.addRow(count,
                    filtered.getAccessIp(),
                    filtered.getAccessCount(),
                    filtered.getBlockReason()
            );

        }
        System.out.println(at.render() + "\n");
        System.out.println("Total rows found : " + count + "\n");

    }

}
