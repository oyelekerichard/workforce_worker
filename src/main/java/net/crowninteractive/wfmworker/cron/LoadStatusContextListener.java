/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author johnson3yo
 */
public class LoadStatusContextListener implements ServletContextListener,ApplicationContextAware {

    private WorkOrderDao dao;
    private ApplicationContext ac;
    
      @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ac = ac;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       dao = ac.getBean("workOrderDao", WorkOrderDao.class);
       System.out.println(">>>>>>>>>>>>Dao >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}
