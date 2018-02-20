/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.crowninteractive.wfmworker.dao.BarChartWidget;
import net.crowninteractive.wfmworker.dao.BarChartWidget_1;

/**
 *
 * @author johnson3yo
 */
public class Dashboard {

    public Dashboard() {
    }

    private List<LowerWidget> districtStatuses = new ArrayList();

    private BigInteger totalClosed;
    private BigInteger totalOpened;
    private List<Map<String, String>> consultants = new ArrayList();
    private List<Map<String, String>> districts = new ArrayList();

    public List<LowerWidget> getDistrictStatuses() {
        return districtStatuses;
    }

    public void setDistrictStatuses(List<LowerWidget> districtStatuses) {
        this.districtStatuses = districtStatuses;
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

    public List<Map<String, String>> getConsultants() {
        return consultants;
    }

    public void setConsultants(List<Map<String, String>> consultants) {
        this.consultants = consultants;
    }

    public List<Map<String, String>> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Map<String, String>> districts) {
        this.districts = districts;
    }

    
    
    

}
