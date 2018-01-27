/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;

/**
 *
 * @author johnson3yo
 */

public class LowerWidget {
    
    @JsonInclude(Include.NON_NULL)
    private String districtName;
    @JsonInclude(Include.NON_NULL)
    private String reportedBy;
    private BigDecimal closed;
    private BigDecimal open;

    public LowerWidget() {
    }

    public LowerWidget(String districtName, BigDecimal closed, BigDecimal open) {
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

    public BigDecimal getClosed() {
        return closed;
    }

    public void setClosed(BigDecimal closed) {
        this.closed = closed;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }
    
    
    
    
    
}
