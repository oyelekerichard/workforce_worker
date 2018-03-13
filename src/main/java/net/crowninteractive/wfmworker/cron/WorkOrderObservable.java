/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class WorkOrderObservable extends Observable implements Runnable {

    @Autowired
    private WorkOrderDao dao;
    private HashMap<Integer,WorkOrder>current;
    
    public WorkOrderObservable(){
        current = new HashMap();
    }

    @Override
    public void run() {
        System.out.println(">>>>>FEtch a list of non migrated work orders ");
           List<WorkOrder>nonMigrated =   dao.findNonMigratedWorkOrders();
           nonMigrated.forEach(dim->{
              if(current.containsKey(dim.getTicketId())){
                 WorkOrder current=  this.current.get(dim.getTicketId());
                 if(!current.getCurrentStatus().equals(dim.getCurrentStatus())){
                      this.notifyObservers(new Gson().
                            toJson(new UpdateMessage(dim.getReferenceTypeData(), dim.getCurrentStatus()))); 
                 }else if(dim.getCurrentStatus().startsWith("MIGRA")){
                     this.current.remove(dim.getTicketId());
                 }
              }else{
                  current.put(dim.getTicketId(), dim);
              }
           
           });
           
          
       

    }

    public void addWorkOrderUpdateListener(WorkOrderObserver woo) {

        super.addObserver(woo);
    }

}
