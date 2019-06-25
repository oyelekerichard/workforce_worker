/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author johnson3yo
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class WorkOrderDaoTest {

    @Autowired
    WorkOrderDao wdao;
    @Autowired
    WorkOrderService ws;

    //@Test
    public void testMigratedWorkOrders() {
        wdao.findNonMigratedWorkOrders();
    }

    //@Test
    public void testCreateWorkorder() throws WfmWorkerException {
        WorkOrderMessage wm = new WorkOrderMessage();
        wm.setBusinessUnit("");
        wm.setAccountType("");
        wm.setAddressLine1("");
        wm.setAddressLine2("");
        wm.setBusinessUnit("");
        wm.setAssignedBy(1);
        wm.setCity("");
        wm.setContactNumber("");
        wm.setCreatedBy(1);
        wm.setCustomerEmail("");
        wm.setCustomerName("ameh crown");
        wm.setCustomerTariff("R2");
        wm.setDescription("");
        wm.setQueueId(1);
        wm.setQueueTypeId(2);
        wm.setReferenceType("");
        wm.setReferenceTypeData("");
        wm.setReportedBy("");
        wm.setSummary("");
        wm.setTicketId(534526);
        wm.setUpdatedBy(1);
        ws.createWorkOrder(wm);
    }

}
