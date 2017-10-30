package com.ef.dao;

import com.ef.model.IpBlackList;
import com.ef.model.LogFile;
import com.ef.util.connection.ConnectionUtil;

/**
 *
 * @author Abed
 */
public class IpBlackListDao {

    public void save(IpBlackList ipBlackList) {
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

    public void fetchAndSaveBlackListByLogFile(LogFile logFile) {
        ConnectionUtil connection = new ConnectionUtil();

        connection.prepareStatement("CALL fetchAndSaveBlackListByLogFile()");
        connection.setInteger(1, logFile.getLogId());

        connection.executeQuery();

        connection.closeConnection();
    }
}
