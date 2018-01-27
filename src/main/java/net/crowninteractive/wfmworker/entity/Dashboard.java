/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.crowninteractive.wfmworker.dao.BarChartWidget;

/**
 *
 * @author johnson3yo
 */
public class Dashboard {

    public Dashboard() {
    }
     
    private List<LowerWidget> districtStatuses = new ArrayList();
    private List<BarChartWidget>conData = new ArrayList();
    private BigInteger totalClosed;
    private BigInteger totalOpened;

    public List<LowerWidget> getDistrictStatuses() {
        return districtStatuses;
    }

    public void setDistrictStatuses(List<LowerWidget> districtStatuses) {
        this.districtStatuses = districtStatuses;
    }

    public List<BarChartWidget> getConData() {
        return conData;
    }

    public void setConData(List<BarChartWidget> conData) {
        this.conData = conData;
    }

    public BigInteger getTotalClosed() {
        return totalClosed;
    }

    public void setTotalClosed(BigInteger totalClosed) {
        this.totalClosed = totalClosed;
    }

    public BigInteger getTotalOpened() {
        return totalOpened;
    }

    public void setTotalOpened(BigInteger totalOpened) {
        this.totalOpened = totalOpened;
    }
    
    
    
    
    
    
}
