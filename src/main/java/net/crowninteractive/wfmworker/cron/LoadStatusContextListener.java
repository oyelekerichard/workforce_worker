/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class LoadStatusContextListener implements ServletContextListener,ApplicationContextAware{

  
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Bean AC>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+ac);
    }

}
