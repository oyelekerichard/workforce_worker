/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.List;

/**
 *
 * @author johnson3yo
 */
public class BarChartWidget {
   
    private String consultant;
    private String district;
    private List<QueueTypeData>queueData;

    public List<QueueTypeData> getQueueData() {
        return queueData;
    }

    public void setQueueData(List<QueueTypeData> queueData) {
        this.queueData = queueData;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    
    
    
    
    
}
