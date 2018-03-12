/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import java.util.Arrays;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.crowninteractive.wfmworker.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author johnson3yo
 */

public class LoadStatusContextListener implements ServletContextListener {

  
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      ApplicationContext ac = ApplicationContextProvider.getApplicationContext();
        System.out.println(">>>>> Bean definition >>>>>>>>>>>>>>>>>>>>>"+Arrays.toString(ac.getBeanDefinitionNames()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
