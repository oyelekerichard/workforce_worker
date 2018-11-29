/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.dao.WorkOrderRemarkDao;
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
public class MORTest {

    private static String fileName = "/home/remark.csv";
    @Autowired
    private WorkOrderRemarkDao wdao;
    @Autowired
    private WorkOrderDao dao;

    @Test
    public void testAddRemark() {

        try (Stream<String> stream = Files.lines(Paths.get(fileName));) {

            stream.forEach(line -> {
                String orderId = line.split(",")[1];
                Double amount = Double.parseDouble(line.split(",")[2]);
                String ticketId =line.split(",")[3];
                String msg = String.format("A ADMINISTRATIVE CHARGE charge of %.2f with order ID %d has been charged to this account.", amount, orderId);
                dao.addRemarkV2("1", ticketId, msg);
                
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
