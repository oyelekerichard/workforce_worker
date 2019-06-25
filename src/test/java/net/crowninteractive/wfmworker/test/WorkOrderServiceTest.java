/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.util.List;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author johnson3yo
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class WorkOrderServiceTest {

    @Autowired
    WorkOrderService service;

    //@Test
    public void testServiceForWorkOrder() {
        List<WorkOrder> workOrders = service.getWorkOrders(23, 1);
        workOrders.stream().forEach(w -> System.out.println(w.getTicketId()));
        assertTrue(workOrders.size() == 100);
    }

    //@Test
    public void testMap() {
        service.generateStaffCode(0);
    }

}
