/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.context.ApplicationContext;
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
        
        WorkOrderObservable processor = new WorkOrderObservable();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        int delay = 50;
        scheduledThreadPool.scheduleAtFixedRate(processor, 0, delay, TimeUnit.SECONDS);     
        processor.addWorkOrderUpdateListener(new WorkOrderObserver());
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
