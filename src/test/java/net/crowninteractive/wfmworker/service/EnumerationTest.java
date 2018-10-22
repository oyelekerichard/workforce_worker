/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import net.crowninteractive.wfmworker.dao.EnumerationWorkOrderDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author johnson3yo
 */
public class EnumerationTest {

    @Autowired
    private WorkOrderDao wdao;
    @Autowired
    private EnumerationWorkOrderDao edao;

    @Test
    public void testMapping() {
       
    }
}
