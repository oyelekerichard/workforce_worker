/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrder;

/**
 *
 * @author uchep
 */
public class WorkOrderEnumerationBody {

    private WorkOrder workOrder;
    private EnumerationWorkOrder enumerationWorkOrder;

    public WorkOrderEnumerationBody() {
    }

    public WorkOrderEnumerationBody(WorkOrder workOrder, EnumerationWorkOrder enumerationWorkOrder) {
        this.workOrder = workOrder;
        this.enumerationWorkOrder = enumerationWorkOrder;
    }
    
    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public EnumerationWorkOrder getEnumerationWorkOrder() {
        return enumerationWorkOrder;
    }

    public void setEnumerationWorkOrder(EnumerationWorkOrder enumerationWorkOrder) {
        this.enumerationWorkOrder = enumerationWorkOrder;
    }
}
