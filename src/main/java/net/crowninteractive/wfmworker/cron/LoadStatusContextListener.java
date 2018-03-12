/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author johnson3yo
 */
public class LoadStatusContextListener implements ServletContextListener {

    @Autowired
    WorkOrderDao dao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        this.dao = (WorkOrderDao) ctx.getBean("workOrderDao");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Wado >>>>>>>>>>>>>>>>>>>>>>>>"+dao);
        System.out.println(">>>>>>>>>>>>Ifrst name for ! >>>>>>"+dao.findUserById(1).getFirstname());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
