package com.ef.dao;

import com.ef.model.IpFilterCriteria;
import com.ef.model.IpFiltered;
import com.ef.model.LogFile;
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
public class IpBlackListDao {

    public void save(IpFiltered ipBlackList) {
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("INSERT INTO ip_blacklist(access_ip , threshold , access_count , block_reason)"
                + " values (?, ?, ?, ?)");
        connection.setString(1, ipBlackList.getAccessIp());
        connection.setInteger(2, ipBlackList.getThreshold());
        connection.setInteger(3, ipBlackList.getAccessCount());
        connection.setString(4, ipBlackList.getBlockReason());

        connection.executeQuery();

        connection.closeConnection();
    }

    public void findAndSaveBlackListByIpFilterCriteria(IpFilterCriteria criteria) {
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("CALL findAndSaveBlackListByLogFile(?, ?, ?, ?, ?)");

        connection.setInteger(1, criteria.getLogId());
        connection.setDate(2, criteria.getStartDate());
        connection.setDate(3, criteria.getEndDate());
        connection.setInteger(4, criteria.getThreshold());
        connection.setString(5, criteria.getBlockMessage());

        connection.executeQuery();

        connection.closeConnection();
    }

    public Integer findCountByLogId(Integer logId) {
        Integer result = 0;
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("SELECT COUNT(*) FORM ip_blacklist WHERE log_id = ?");

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

    public List<IpFiltered> findAllByLogId(Integer logId) {
        IpFiltered ipFiltered = null;
        List<IpFiltered> result = new ArrayList<>();
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("SELECT * FROM ip_blacklist WHERE log_id = ?");

        connection.setInteger(1, logId);
        ResultSet rs = connection.executeQueryWithResultSet();

        try {
            while (rs.next()) {
                ipFiltered = new IpFiltered();
                ipFiltered.setAccessIp(rs.getString("access_ip"));
                ipFiltered.setAccessCount(rs.getInt("access_count"));
                ipFiltered.setBlockReason(rs.getString("block_reason"));

                result.add(ipFiltered);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogFileDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.closeConnection();
        }

        return result;
    }

}
