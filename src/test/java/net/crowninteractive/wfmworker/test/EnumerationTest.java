/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import net.crowninteractive.wfmworker.service.EnumService;
import net.crowninteractive.wfmworker.service.Token;
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
public class EnumerationTest {
    
    @Autowired
    private EnumService enumService;
    
    @Test
    public void approveWorkOrder(){
//        String approveWorkOrders = enumService.approveWorkOrders(
//                new Token().setTokens(new String[]{"ztdyogo7yQS1VimSqdU1LSRyeU9OEN"}));
//        System.out.println("-----------------"+approveWorkOrders+"--------------------");
    }
    
}
