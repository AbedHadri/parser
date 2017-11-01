package com.ef.task;

import com.ef.consts.ParseConsts;
import com.ef.dao.LogEntryDao;
import com.ef.dao.LogFileDao;
import com.ef.model.LogFile;
import com.ef.util.connection.ConnectionUtil;
import com.ef.util.converter.DateUtils;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
public class LogFileImport {

    public LogFile saveLogFileDataAndParse(String logFilePath) {
        LogFileDao dao = new LogFileDao();
        String[] parts = logFilePath.split("/");

        String fileName = parts[parts.length - 1];

        System.out.println("Importing, please wait..");
        LogFile logFile = new LogFile(fileName);
        logFile = dao.saveLogFileData(logFile);

        try {

            parseAndSaveInDatabase(logFilePath, logFile.getLogId());
            System.out.println(generateFinalResultMessage(logFilePath, logFile.getLogId()));
        } catch (Exception ex) {
            Logger.getLogger(LogFileImport.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        return logFile;
    }

    /////////////
    // private methods here
    ////////////
    // for the 'Java' way please use the method preceding this one.
    // There is no way better and faster than this 
    // (no more than 1 min for 100K lines and no SSD), for better performance
    // and smoother testing this one is perferred.
    //
    private void parseAndSaveInDatabase(String logFilePath, int logId) {

        // this type of queries is not allowed as procedure in MySQL.
        // note than in some old MySQL versions parameterization of file path CAN FAIL!
        // therefore please use some new version of MySQL.
        String sqlQuery = "load data infile ? into table log_entry"
                + " fields terminated by '\\|' enclosed by '' lines terminated by '\\n'"
                + " (@col1,@col2,@col3,@col4,@col5) "
                + " set log_id = ? , "
                + " access_date = @col1, "
                + " access_ip = @col2, "
                + " request_header = @col3, "
                + " response_code = @col4, "
                + " user_agent= @col5;";

        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement(sqlQuery);

        connection.setString(1, logFilePath);
        connection.setInteger(2, logId);

        connection.executeQuery();

        connection.closeConnection();
    }

    /* 
    since 'Java tool' was required in the test document. this is the 'Java' way of doing it (Row by row in bulks).
     */
    private void parseAndSaveInDatabaseJavaWay(String logFilePath, int logId) throws Exception {
        Reader reader = new FileReader(logFilePath);
        ConnectionUtil connection = new ConnectionUtil();
        int total = 0;
        connection.prepareStatement("INSERT INTO log_entry"
                + " (log_id ,access_date, access_ip , request_header , response_code  , user_agent)"
                + " VALUES (?, ?, ?, ?, ?, ?)");
        try (BufferedReader bufferedReader
                = new BufferedReader(reader, 1024 * 50)) {

            reader = new FileReader(logFilePath);

            total = getFileLineCount(logFilePath);

            String line = bufferedReader.readLine();

            for (int counter = 0; line != null; counter++) {

                String[] rowContent = line.split(ParseConsts.DELIMITER);

                long readTime = System.currentTimeMillis();
                line = bufferedReader.readLine();
                readTime = System.currentTimeMillis() - readTime;

                if (rowContent.length == ParseConsts.LOG_PARAM_COUNT) {
                    connection.setInteger(1, logId);
                    connection.setDate(2, DateUtils.toDate(rowContent[0]));
                    connection.setString(3, rowContent[1]);
                    connection.setString(4, rowContent[2]);
                    connection.setInteger(5, Integer.parseInt(rowContent[3]));
                    connection.setString(6, rowContent[4]);

                    connection.addBatch();

                    // to split into batches of size BATCH_SIZE 
                    // or if this is the last line run in order not to lose last batch if size < BATCH_SIZE
                    if (counter % ParseConsts.BATCH_SIZE == 0 || line == null) {
                        long insertTime = System.currentTimeMillis();
                        connection.executeBatch();
                        insertTime = System.currentTimeMillis() - insertTime;

                        connection.clearBatch();
                        System.out.printf("%d of %d imported , %d %% finished inserted in %dms\r",
                                counter, total, (int) ((double) ((double) counter / total) * 100), insertTime);

                    }
                } else {
                    System.err.println("row (" + counter + ") is corrupted. (" + (ParseConsts.LOG_PARAM_COUNT - rowContent.length) + ") parameter(s) missing.");
                }

            }
        } finally {

            if (connection != null) {
                connection.clearBatch();
            }
        }
    }

    private int getFileLineCount(String logFilePath) {
        File file = new File(logFilePath);
        int nLines = 0;
        try {
            nLines = Files.readLines(file, StandardCharsets.UTF_8, new LineProcessor<Integer>() {
                int count = 0;

                @Override
                public boolean processLine(String line) throws IOException {
                    count++;
                    return true;
                }

                @Override
                public Integer getResult() {
                    return count;
                }

            });
        } catch (IOException ex) {
            Logger.getLogger(LogFileImport.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        return nLines;
    }

    private String generateFinalResultMessage(String filePath, int logId) {
        String message = "";
        Integer importedTotalRows = 0;
        Integer totalRowsInFile = getFileLineCount(filePath);

        LogEntryDao entryDao = new LogEntryDao();
        importedTotalRows = entryDao.findCountByLogId(logId);

        if (importedTotalRows == totalRowsInFile) {
            message = "All " + totalRowsInFile + " rows where imported successfully.";
        } else if (importedTotalRows < totalRowsInFile) {
            message = "Only " + importedTotalRows + " where imported out of " + totalRowsInFile + " rows.";
        } else {
            message = importedTotalRows + " where imported out of " + totalRowsInFile + " rows. Duplicates may have occured.";
        }

        return message;
    }

}
