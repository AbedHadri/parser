package com.ef;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ef.dao.LogEntryDao;
import com.ef.model.LogEntry;
import com.ef.util.output.TablePrint;
import java.util.List;

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

    @Parameter(names = {"--endDate", "-e"})
    private static String endDate;

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
        List<LogEntry> findByAccessIp = dao.findByAccessIp(ipToQuery);
        TablePrint.printLogEntryTable(findByAccessIp);
    }

}
