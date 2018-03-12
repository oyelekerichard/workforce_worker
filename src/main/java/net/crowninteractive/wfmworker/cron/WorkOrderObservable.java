/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import java.util.Observable;

/**
 *
 * @author johnson3yo
 */
public class WorkOrderObservable extends Observable {
    
    
    public void addWorkOrderUpdateListener(WorkOrderObserver woo){
        super.addObserver(woo);
    }
    
}
