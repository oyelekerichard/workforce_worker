/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author johnson3yo
 */

public class LoadStatusContextListener implements ServletContextListener {

  
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println(">>>>>>>>>>>>>>>>Sce >>>>>>>>>>>>>>>>>>>>>"+sce);
        ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        System.out.println(">>>>>>>>>App CTC >>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>...CTX >>>>>>>>>"+appCtx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
