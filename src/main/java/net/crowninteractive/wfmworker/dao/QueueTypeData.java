/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.math.BigDecimal;

/**
 *
 * @author johnson3yo
 */
public class QueueTypeData {
    private Integer queueTypeId;
    private String queueTypeName;
    private BigDecimal workOrderCount;

   

    public BigDecimal getWorkOrderCount() {
        return workOrderCount;
    }

    public void setWorkOrderCount(BigDecimal workOrderCount) {
        this.workOrderCount = workOrderCount;
    }

    public Integer getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(Integer queueTypeId) {
        this.queueTypeId = queueTypeId;
    }

    public String getQueueTypeName() {
        return queueTypeName;
    }

    public void setQueueTypeName(String queueTypeName) {
        this.queueTypeName = queueTypeName;
    }

   
    
    
    
}
