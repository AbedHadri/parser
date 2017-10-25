package com.ef.dao;

import com.ef.model.LogEntry;
import com.ef.util.connection.ConnectionUtil;
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
