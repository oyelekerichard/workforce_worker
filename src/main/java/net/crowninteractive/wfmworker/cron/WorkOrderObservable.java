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
public class WorkOrderObservable extends Observable {

    @Autowired
    private WorkOrderDao dao;
    private HashMap<Integer, WorkOrder> current;

    public WorkOrderObservable() {
        System.out.println(">>>>>>Setting Hash Map ?>>>>>>>>>>>>>>>>>");
        current = new HashMap();
    }

    public void run() {
        try {
          
            System.out.println(">>>>>>>Dao <>>>>>>>>>>>>>>>>>>>>>>>"+dao);
            System.out.println(">>>>>FEtch a list of non migrated work orders >>>>>");
            List<WorkOrder> nonMigrated = dao.findNonMigratedWorkOrders();
            if (nonMigrated != null) {
                if (nonMigrated.size() > 0) {
                    System.out.println(">>>>>>>>>>>>>.....Found>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>" + nonMigrated.size());
                    nonMigrated.forEach(dim -> {
                        if (current.containsKey(dim.getTicketId())) {
                            WorkOrder current = this.current.get(dim.getTicketId());
                            if (!current.getCurrentStatus().equals(dim.getCurrentStatus())) {
                                this.notifyObservers(new Gson().
                                        toJson(new UpdateMessage(dim.getReferenceTypeData(), dim.getCurrentStatus())));
                            } else if (dim.getCurrentStatus().startsWith("MIGRA")) {
                                this.current.remove(dim.getTicketId());
                            }
                        } else {
                            current.put(dim.getTicketId(), dim);
                        }

                    });
                } else {
                    System.out.println(">>>>>>Notttttttttttttttttttttt FOund >>>>>>>>>>>>>>>>>>>>>>>");
                }

            }
        } catch (Exception e) {
            System.out.println(">>>>>>>>Exception occured here >>>>>>>>>>>>>>>>>>>>>");
            e.printStackTrace();
        }

    }

    {
    }

    public void addWorkOrderUpdateListener(WorkOrderObserver woo) {

        super.addObserver(woo);
    }

   

}
