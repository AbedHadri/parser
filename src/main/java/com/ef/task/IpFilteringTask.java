package com.ef.task;

import com.ef.dao.IpBlackListDao;
import com.ef.model.IpFilterCriteria;
import com.ef.model.LogFile;
import com.ef.util.output.TablePrint;
import java.util.Date;

/**
 *
 * @author Abed
 */
public class IpFilteringTask {

    public void filterAndInsertToBlackList(LogFile logFile, Date startDate, String duration, Integer threshold) {
        System.out.println("Populating black list..");
        IpFilterCriteria criteria = new IpFilterCriteria(logFile.getLogId(), startDate, duration, threshold);
        criteria.setBlockMessage("Exceeded threshold.");

        IpBlackListDao dao = new IpBlackListDao();

        dao.findAndSaveBlackListByIpFilterCriteria(criteria);
        TablePrint.printBlackListTable(dao.findAllByLogId(logFile.getLogId()));
    }
}
