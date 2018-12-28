/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import net.crowninteractive.wfmworker.misc.EnumerationRequestModel;

/**
 *
 * @author CROWN INTERACTIVE
 */
public class RequestEnumerationBody {
    
    private EnumerationRequestModel workOrderTemp;
    private EnumerationWorkOrder enumerationWorkOrder;

    public RequestEnumerationBody(EnumerationRequestModel workOrderTemp, EnumerationWorkOrder enumerationWorkOrder) {
        this.workOrderTemp = workOrderTemp;
        this.enumerationWorkOrder = enumerationWorkOrder;
    }

    public EnumerationRequestModel getWorkOrderTemp() {
        return workOrderTemp;
    }

    public void setWorkOrderTemp(EnumerationRequestModel workOrderTemp) {
        this.workOrderTemp = workOrderTemp;
    }

    public EnumerationWorkOrder getEnumerationWorkOrder() {
        return enumerationWorkOrder;
    }

    public void setEnumerationWorkOrder(EnumerationWorkOrder enumerationWorkOrder) {
        this.enumerationWorkOrder = enumerationWorkOrder;
    }

   
}
