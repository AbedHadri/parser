package com.ef.model;

import com.ef.model.base.IpAccess;

/**
 *
 * @author Abed
 */
public class IpBlackList extends IpAccess {

    private Integer threshold;
    private Integer accessCount;
    private String blockReason;

    public Integer getThreshold() {
        return threshold;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

}
