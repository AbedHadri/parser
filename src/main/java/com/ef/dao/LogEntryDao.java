package com.ef.dao;

import com.ef.model.LogEntry;
import com.ef.model.LogFile;
import com.ef.util.connection.ConnectionUtil;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
public class LogEntryDao {

    public List<LogEntry> findByAccessIp(String accessIp) {
        List<LogEntry> resultEntries = new ArrayList<>();

        ConnectionUtil con = new ConnectionUtil();
        try {
            con.prepareStatement("CALL findByAccessIp(?)");
            con.setString(1, accessIp);
            ResultSet rs = con.executeQueryWithResultSet();
            while (rs.next()) {
                LogEntry temp = retriveFromResultSet(rs);
                resultEntries.add(temp);
            }
        } catch (Exception e) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            con.closeConnection();
        }

        return resultEntries;
    }

    public List<LogEntry> findByTimeIntervalAndDuration(Date startDate, Date endDate, String duration) {
        List<LogEntry> resultEntries = new ArrayList<>();

        ConnectionUtil con = new ConnectionUtil();
        try {
            con.prepareStatement("CALL findByTimeIntervalAndDuration(?)");
            con.setDate(1, startDate);
            con.setDate(1, endDate);
            ResultSet rs = con.executeQueryWithResultSet();
            while (rs.next()) {
                LogEntry temp = retriveFromResultSet(rs);
                resultEntries.add(temp);
            }
        } catch (Exception e) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            con.closeConnection();
        }

        return resultEntries;
    }

    public List<LogEntry> findLogEntriesByStartDateAndEndDateAndThreshold() {
        List<LogEntry> resultEntries = new ArrayList<>();
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("CALL findLogEntriesByStartDateAndEndDateAndThreshold(? , ?, ?)");

        return resultEntries;
    }

    public Integer findCountByLogId(Integer logId) {
        Integer result = 0;
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("SELECT COUNT(*) FROM log_entry WHERE log_id = ?");

        connection.setInteger(1, logId);

        ResultSet rs = connection.executeQueryWithResultSet();
        try {
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogFileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
        return result;
    }

    //
    // private methods
    // 
    private LogEntry retriveFromResultSet(ResultSet rs) {
        LogEntry logEntry = new LogEntry();

        try {
            logEntry.setAccessDate(rs.getDate(LogEntry.ACCESS_DATE));
            logEntry.setAccessIp(rs.getString(LogEntry.ACCESS_IP));
            logEntry.setRequestHeader(rs.getString(LogEntry.REQUEST_HEADER));
            logEntry.setResponseCode(rs.getInt(LogEntry.RESPONSE_CODE));
            logEntry.setUserAgent(rs.getString(LogEntry.USER_AGENT));
        } catch (SQLException ex) {
            Logger.getLogger(LogEntryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logEntry;
    }

}
