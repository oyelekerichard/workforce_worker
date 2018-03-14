/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class WorkOrderObservable extends TimerTask{

    @Autowired
    private WorkOrderDao dao;
    @Autowired
    private WorkOrderObserver woob;
    private HashMap<Integer, WorkOrder> current;

    public WorkOrderObservable() {
        current = new HashMap();
    }

    @Override
    public void run() {
        try {      
            List<WorkOrder> nonMigrated = dao.findNonMigratedWorkOrders();
            if (nonMigrated != null) {
                if (nonMigrated.size() > 0) {
                   nonMigrated.forEach(dim -> {
                        if (current.containsKey(dim.getTicketId())) {
                            WorkOrder current = this.current.get(dim.getTicketId());
                            if (!current.getCurrentStatus().equals(dim.getCurrentStatus())) {
                                woob.update(new Gson().
                                        toJson(new UpdateMessage(dim.getReferenceTypeData(), dim.getCurrentStatus())));
                                this.current.put(current.getTicketId(), dim);
                            } else if (dim.getCurrentStatus().startsWith("MIGRA")) {
                                this.current.remove(dim.getTicketId());
                            }
                        } else {
                            current.put(dim.getTicketId(), dim);
                        }
                    });
                } 
            }
        } catch (Exception e) {
             e.printStackTrace();
        }

    }

    {
    }

   
   

}
