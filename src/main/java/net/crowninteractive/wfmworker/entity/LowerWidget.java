/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author johnson3yo
 */

public class LowerWidget {
    
    @JsonInclude(Include.NON_NULL)
    private String districtName;
    @JsonInclude(Include.NON_NULL)
    private String reportedBy;
    private BigInteger closed;
    private BigInteger open;

    public LowerWidget() {
    }

    public LowerWidget(String districtName, BigInteger closed, BigInteger open) {
        this.districtName = districtName;
        this.closed = closed;
        this.open = open;
    }
    
    

    public String getDistrictName() {
        return districtName;
    }

    public void getDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public BigInteger getClosed() {
        return closed;
    }

    public void setClosed(BigInteger closed) {
        this.closed = closed;
    }

    public BigInteger getOpen() {
        return open;
    }

    public void setOpen(BigInteger open) {
        this.open = open;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }
    
    
    
    
    
}
