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
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author johnson3yo
 */

public class LoadStatusContextListener implements ServletContextListener {

    private WorkOrderDao dao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        dao = ctx.getBean(WorkOrderDao.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>Work DAO >>>>>>>>>>>>>>>>>>>>>>>>>"+dao);
      
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

 

}
