package com.ef.model;

import com.ef.exception.DurationNotKnowException;
import com.ef.util.converter.DateUtils;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abed
 */
public class IpFilterCriteria {

    private Integer logId;
    private Date startDate;
    private Date endDate;
    private String duration;
    private Integer threshold;
    private String blockMessage = "";

    public IpFilterCriteria() {
    }

    public IpFilterCriteria(Integer logId, Date startDate, String duration, Integer threshold) {
        this.logId = logId;
        this.startDate = startDate;
        this.setDuration(duration);
        this.threshold = threshold;
    }

    public Integer getLogId() {
        return logId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        if (endDate == null) {
            try {
                this.endDate = DateUtils.getDateAfterDuration(startDate, duration);
            } catch (DurationNotKnowException ex) {
                Logger.getLogger(IpFilterCriteria.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
        }
        return endDate;
    }

    public String getDuration() {
        return duration;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public String getBlockMessage() {
        return blockMessage;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDuration(String duration) {
        this.duration = duration;

    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public void setBlockMessage(String blockMessage) {
        this.blockMessage = blockMessage;
    }

}
