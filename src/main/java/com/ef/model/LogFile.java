package com.ef.model;

import java.util.Date;

/**
 *
 * @author Abed
 */
public class LogFile {

    public static final String LOG_ID = "log_id";
    public static final String FILE_NAME = "file_name";
    public static final String DATE_IMPORTED = "import_date";

    private Integer logId;
    private String fileName;
    private Date dateImported;

    public LogFile() {
    }

    public LogFile(String fileName) {
        this.fileName = fileName;
    }

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
