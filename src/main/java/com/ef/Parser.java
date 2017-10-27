package com.ef;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ef.dao.LogEntryDao;
import com.ef.model.LogEntry;
import com.ef.task.LogFileImport;
import com.ef.util.output.TablePrint;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
@Parameters(separators = "=")
public class Parser {

    @Parameter(names = {"--startDate", "-s"})
    private static String startDate;

    @Parameter(names = {"--accesslog", "-al"})
    private static String accessLogDirectory;

    @Parameter(names = {"--threshold", "-t"})
    private static Integer threshold;

    @Parameter(names = {"--ipToQuery", "-ip"})
    private String ipToQuery;

    public static void main(String[] args) {
        Parser parser = new Parser();
        JCommander.newBuilder()
                .addObject(parser)
                .build()
                .parse(args);
        parser.run();

    }

    public void run() {
        LogEntryDao dao = new LogEntryDao();
        if (ipToQuery != null && !ipToQuery.isEmpty()) {
            List<LogEntry> findByAccessIp = dao.findByAccessIp(ipToQuery);
            TablePrint.printLogEntryTable(findByAccessIp);
        } else if (accessLogDirectory != null && threshold != null) {
            try {
                // please disregard this 2 here it should be an iterative from a parent table ... just ignore it :D
                new LogFileImport().parseAndSaveInDatabase(accessLogDirectory, 2);
            } catch (Exception ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
