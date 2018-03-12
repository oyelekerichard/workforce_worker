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

   
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
      
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        System.out.println(">>>>>>>>AppCTAX >>>>>>>>>>>>>>>>>>"+appCtx);
        System.out.println("?>>>>>>>>>>CAPp CASbizoo    >>>>>>>>>>>>>>"+Arrays.toString(appCtx.getBeanDefinitionNames()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
