/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.crowninteractive.wfmworker.ReportReceiver;
import net.crowninteractive.wfmworker.dao.UsersDao;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import org.apache.commons.mail.EmailException;
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
public class DynamicNercReportGenerationTest {
    
    @Autowired private ReportReceiver recieve;
      @Autowired
    private UsersDao usersDao;
    
    @Test(expected = EmailException.class)
    public void generate() throws IOException, FileNotFoundException, EmailException, WfmWorkerException{
        String email = "mika.alanko@crowninteractive.com";
        recieve.processWriteV2("2018-01-01", "2018-04-01", email);
    }
    
}
