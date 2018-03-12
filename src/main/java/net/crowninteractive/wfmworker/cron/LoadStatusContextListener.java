/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author johnson3yo
 */

public class LoadStatusContextListener implements ServletContextListener {

  
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println(">>>>>>>>>>>>>>>>Sce >>>>>>>>>>>>>>>>>>>>>"+sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
