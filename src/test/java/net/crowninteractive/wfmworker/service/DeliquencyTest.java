/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import java.util.Date;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Queue;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author johnson3yo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class DeliquencyTest {

   @Autowired
   private WorkOrderDao wdao;

   @Test
   public void testCreateWorkOrder() {
        WorkOrder.WorkOrderBuilder builder = new WorkOrder.WorkOrderBuilder();
        builder.setAddressLine1("Lekki Phase 1").setBusinessUnit("Lekki").setAmount(Double.valueOf("2000"))
                .setCity("Lekki").setContactNumber("0123456789").setCurrentBill(Double.valueOf("2000"))
                .setDescription("Payment").setDueDate(new Date())
                .setLastPaymentAmount(Double.valueOf("1000")).setLastPaymentDate(new Date())
                .setPreviousOutstanding(Double.valueOf("1000")).setToken("64646").setDebtBalanceAmount(0.0)
                .setPurpose("Bill Payment").setReportedBy("Admin").setSummary("Payment before disconnection").setQueueType(new QueueType(1))
                .setCreateTime(new Date()).setCurrentStatus("OPEN").setPriority("Low").setReferenceType("Billing ID").setQueue(new Queue(1))
                .setState("Lagos").setChannel("EMCC").setTicketId(-934);

        WorkOrder build = builder.build();
        wdao.save(build);
   }
   
}