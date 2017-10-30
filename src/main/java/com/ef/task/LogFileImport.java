package com.ef.task;

import com.ef.consts.ParseConsts;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author Abed
 */
public class LogFileImport {

    public void saveLogFileDataAndParse(String logFilePath) {
        LogFileDao dao = new LogFileDao();
        String[] parts = logFilePath.split("/");

        LogFile logFile = new LogFile(parts[parts.length - 1]);

        logFile = dao.saveLogFileData(logFile);

        try {
            parseAndSaveInDatabase(logFilePath, logFile.getLogId());
        } catch (Exception ex) {
            Logger.getLogger(LogFileImport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void parseAndSaveInDatabase(String logFilePath, int logId) throws Exception {
        Reader reader = new FileReader(logFilePath);
        ConnectionUtil connection = new ConnectionUtil();
        int total = 0;
        connection.prepareStatement("INSERT INTO log_entry"
                + " (log_id ,access_date, access_ip , request_header , response_code  , user_agent)"
                + " VALUES (?, ?, ?, ?, ?, ?)");
        try (BufferedReader bufferedReader
                = new BufferedReader(reader, 1024 * 50)) {

            reader = new FileReader(logFilePath);

            File file = new File(logFilePath);
            total = getLineCount(file);

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

    //
    // private methods here
    //
    /* 
    could have been done with Apache commons as in above but just to try this Guava iterator too.
     */
    private int getLineCount(File file) throws IOException {
        int nLines = Files.readLines(file, StandardCharsets.UTF_8, new LineProcessor<Integer>() {
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
        return nLines;
    }

}
