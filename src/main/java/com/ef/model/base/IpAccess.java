package com.ef.model.base;

/**
 *
 * @author Abed
 */
public class IpAccess {

    public static final String ACCESS_IP = "access_ip";

    protected Integer logId;
    protected String accessIp;

    public String getAccessIp() {
        return accessIp;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

}
