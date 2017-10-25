package com.ef.model;

import com.ef.model.base.IpAccess;
import java.util.Date;

/**
 *
 * @author Abed
 */
public class RemoteIpStats extends IpAccess{

    private Date startDate;
    private Date endDate;
    private String duration;
    private Integer accessCount;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDuration() {
        return duration;
    }

    public Integer getAccessCount() {
        return accessCount;
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

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

}
