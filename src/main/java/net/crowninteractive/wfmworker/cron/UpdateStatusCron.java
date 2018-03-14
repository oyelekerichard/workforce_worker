/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import java.util.HashMap;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class UpdateStatusCron {

    @Autowired 
    private WorkOrderObservable wob;
    
    @Scheduled(fixedDelay = 10000)
    public void checkForNewUpdates() {
        System.out.println(">>>>>>Scheduler running >>>>>>>>>>>>>>>>");
        wob.run();
    }

}
