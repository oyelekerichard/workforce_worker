/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import java.util.Arrays;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author johnson3yo
 */
@Component
public class LoadStatusContextListener implements ServletContextListener,ApplicationContextAware {

    private WorkOrderDao dao;
    private static ApplicationContext ac;
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Accp >>>>>>>>>>>>>>>>>>>>>Cntext ");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
     }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      
    }

  

}
