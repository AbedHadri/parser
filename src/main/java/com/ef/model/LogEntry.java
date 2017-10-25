package com.ef.model;

import com.ef.model.base.IpAccess;
import java.util.Date;

/**
 *
 * @author Abed
 */
public class LogEntry extends IpAccess {

    // database column names
    public static final String ACCESS_DATE = "access_date";
    public static final String REQUEST_HEADER = "request_header";
    public static final String RESPONSE_CODE = "response_Code";
    public static final String USER_AGENT = "user_agent";

    private Date accessDate;
    private String requestHeader;
    private Integer responseCode;
    private String userAgent;

    public LogEntry() {
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
