/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.math.BigDecimal;
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
    private BigDecimal totalClosed;
    private BigDecimal totalOpened;

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

    public BigDecimal getTotalClosed() {
        return totalClosed;
    }

    public void setTotalClosed(BigDecimal totalClosed) {
        this.totalClosed = totalClosed;
    }

    public BigDecimal getTotalOpened() {
        return totalOpened;
    }

    public void setTotalOpened(BigDecimal totalOpened) {
        this.totalOpened = totalOpened;
    }
    
    
    
    
    
    
}
