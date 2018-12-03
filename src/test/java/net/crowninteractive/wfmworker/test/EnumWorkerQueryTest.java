/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.util.List;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.misc.EnumerationWorkOrderDownloadModel;
import net.crowninteractive.wfmworker.service.EnumService;
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
public class EnumWorkerQueryTest {
   @Autowired  private WorkOrderDao wdao;
   @Autowired private EnumService service;
   
   
   @Test
   public void testQuery(){
       List<EnumerationWorkOrderDownloadModel> workOrders = wdao.getRequests("LEKKI", "create_time", "create_time", null, null, null, null, null, null, null);
       //  workOrders.stream().map(w -> w.getTransformer()).forEach(System.out::println);
   }
   
  
}
