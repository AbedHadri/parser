package com.ef.dao;

import com.ef.model.LogFile;
import com.ef.util.connection.ConnectionUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
public class LogFileDao {

    public LogFile findLogFileByFileName(String fileName) {
        LogFile logFile = null;
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("CALL findLogFileByFileName(?)");

        connection.setString(1, fileName);

        ResultSet rs = connection.executeQueryWithResultSet();
        try {
            if (rs.next()) {
                logFile = retrieveLogFileDataFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogFileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
        return logFile;

    }

    public LogFile saveLogFileData(LogFile logFile) {
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("INSERT INTO log_file( log_id , file_name) "
                + " VALUES (?, ?) ");

        logFile.setLogId(findNextLogId());

        connection.setInteger(1, logFile.getLogId());
        connection.setString(2, logFile.getFileName());

        connection.executeQuery();
        connection.closeConnection();
        return logFile;
    }

    public int findNextLogId() {
        int logId = 0;
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("SELECT MAX(log_id) log_id FROM log_file");

        ResultSet rs = connection.executeQueryWithResultSet();
        try {
            if (rs.next()) {
                logId = rs.getInt(LogFile.LOG_ID) + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogFileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();

        return logId;
    }

    private LogFile retrieveLogFileDataFromResultSet(ResultSet rs) {
        LogFile logFile = new LogFile();

        try {
            logFile.setLogId(rs.getInt(LogFile.LOG_ID));
            logFile.setFileName(rs.getString(LogFile.FILE_NAME));
            logFile.setDateImported(rs.getDate(LogFile.DATE_IMPORTED));
        } catch (SQLException ex) {
            Logger.getLogger(LogFileDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return logFile;
    }
}
