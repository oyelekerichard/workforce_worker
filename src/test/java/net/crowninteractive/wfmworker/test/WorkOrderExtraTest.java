/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.util.Date;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.dao.WorkOrderExtraDao;
import net.crowninteractive.wfmworker.entity.Queue;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderExtra;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author johnson3yo
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class WorkOrderExtraTest {

    @Autowired
    WorkOrderDao dao;
    @Autowired
    WorkOrderExtraDao dao2;

    //@Test
    public void createWorkOrderExtra() {

        WorkOrder wo = new WorkOrder();
        wo.setBusinessUnit("GBAGS");
        wo.setAddressLine1("");
        wo.setAddressLine2("");
        wo.setQueueId(new Queue(1));
        wo.setQueueTypeId(new QueueType(1));
        wo.setTicketId(-311);
        wo.setContactNumber("");
        wo.setCustomerName("");
        wo.setOwnerId(1);
        wo.setDescription("");
        wo.setReportedBy("");
        wo.setCreateTime(new Date());
        wo.setCurrentStatus("OPEN");
        wo.setCustomerTariff("");
        wo.setCity("");
        wo.setPriority("Low");
        wo.setReferenceType("Billing ID");
        wo.setReferenceTypeData("");
        wo.setState("Lagos");
        wo.setSummary("");
        wo.setToken(RandomStringUtils.randomAlphanumeric(30));
        wo.setChannel("EMCC");
        wo.setDebtBalanceAmount(new Double(0.00));

        WorkOrderExtra woe = new WorkOrderExtra();
        woe.setConnectionType("654");
        woe.setTransformer("4643");
        WorkOrder w = dao.save(wo);
        woe.setId(w);
        dao2.save(woe);

    }

}
