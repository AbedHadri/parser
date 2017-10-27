package com.ef.model;

import java.util.Date;

/**
 *
 * @author Abed
 */
public class LogFile {

    private Integer logId;
    private String fileName;
    private Date dateImported;

    public Integer getLogId() {
        return logId;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getDateImported() {
        return dateImported;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDateImported(Date dateImported) {
        this.dateImported = dateImported;
    }

}
