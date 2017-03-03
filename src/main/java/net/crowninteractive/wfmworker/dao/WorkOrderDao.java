
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------

import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {
    public WorkOrder findById(int id) {
        String          query   = String.format("select * from work_order where id=?1");
        List<WorkOrder> options = getEntityManager().createNativeQuery(query, WorkOrder.class).setParameter(1,
                                      id).getResultList();

        if ((options != null) &&!options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public List<WorkOrder> findTempWorkOrders() {
        String query =
            String.format(
                "select * from work_order where id in (select work_order_id from escalation_temp where `type`='CREATE'  )");

        return getEntityManager().createNativeQuery(query, WorkOrder.class).getResultList();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
