package com.ef;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ef.dao.LogEntryDao;
import com.ef.model.LogEntry;
import com.ef.task.LogFileImport;
import com.ef.util.output.TablePrint;
import com.ef.validate.DurationValueValidation;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
@Parameters(separators = "=" , commandNames = {"--help"})
public class Parser {

    @Parameter(names = {"--startDate", "-s"}, description = "The date that will be the start point for queries.")
    private Date startDate;

    @Parameter(names = {"--duration", "-d"}, validateValueWith = DurationValueValidation.class,
            description = "The duration you wish to query for , this can be either daily or hourly.")
    private String duration;

    @Parameter(names = {"--accesslog", "-al"},
            description = "The path to the log you wish to import and query.")
    private String accessLogDirectory;

    @Parameter(names = {"--threshold", "-t"},
            description = "The threshold you wish to be considered the limit and any value equal or above will be blacklisted.")
    private Integer threshold;

    @Parameter(names = {"--ipToQuery", "-ip"},
            description = "Get the request list made by the IP entered.")
    private String ipToQuery;

    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help = false;

    public static void main(String[] args) {
        Parser parser = new Parser();

        JCommander jc = JCommander.newBuilder()
                .addObject(parser)
                .build();
        if (parser.help) {
            jc.usage();
            return;
        } else {
            try {
                jc.parse(args);
                parser.run();
            } catch (RuntimeException e) {
                Logger.getLogger(Parser.class.getName()).log(Level.WARNING ,e.getMessage());
            }

        }

    }

    public void run() {

        if (ipToQuery != null && !ipToQuery.isEmpty()) {
            LogEntryDao dao = new LogEntryDao();
            List<LogEntry> findByAccessIp = dao.findByAccessIp(ipToQuery);
            TablePrint.printLogEntryTable(findByAccessIp);

        } else if (accessLogDirectory != null && threshold != null && startDate != null && duration != null) {
            try {
                new LogFileImport().saveLogFileDataAndParse(accessLogDirectory);
            } catch (Exception ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Parameters missing , try using --help or -h to see more details.");
        }
    }

}
