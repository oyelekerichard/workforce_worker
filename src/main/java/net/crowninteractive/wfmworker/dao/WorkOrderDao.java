
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import java.util.Date;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.List;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrderUpdate;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {

    public WorkOrder findById(int id) {
        String query = String.format("select * from work_order where id=?1");
        List<WorkOrder> options = getEntityManager().createNativeQuery(query, WorkOrder.class).setParameter(1,
                id).getResultList();

        if ((options != null) && !options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public List<WorkOrder> findTempWorkOrders() {
        String query
                = String.format(
                        "select * from work_order where id in (select work_order_id from escalation_temp where `type`='CREATE'  )");

        return getEntityManager().createNativeQuery(query, WorkOrder.class).getResultList();
    }

    public List<WorkOrder> getWorkOrderByParams(String district, String from, String to) {
        String qry = "select * from work_order where is_active=1 and business_unit='%s' and date(create_time) "
                + ">= date('%s') and date(create_time) <= date('%s')";
        Query q = getEntityManager().createNativeQuery(String.format(qry, district,from,to), WorkOrder.class);
        return q.getResultList();
    }

    public String getCustomerName(WorkOrder w) {
        String name;

        try {
            if (w.getReferenceType() != null && w.getReferenceTypeData() != null && w.getReferenceType().equals("Billing ID") && !w.getReferenceTypeData().equals("Not Applicable")) {
                String qry = "select name from esbdb.customer where account_number='%s' order by customer_id desc limit 1";
                Query q = getEntityManager().createNativeQuery(String.format(qry, w.getReferenceTypeData()));
                String fo  = (String )q.getSingleResult();
                name = fo == null ? w.getReportedBy() : fo;
               
            } else {
                name = w.getReportedBy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            name = "n/a";
        }

        return name;
    }

    public Date getDateResolved(WorkOrder w) {
        if (w.getIsClosed() == 1) {
            return w.getClosedTime();
        } else if (w.getCurrentStatus().toLowerCase().equals("completed") || w.getCurrentStatus().toLowerCase().equals("resolved")) {
            if (w.getWorkOrderStatusId() == null) {
                return null;
            }
            Query q = getEntityManager().createNativeQuery(
                    String.format("select create_time from work_order_update  where work_order_id=%d and "
                            + "work_order_status_id=%d order by id desc limit 1", w.getId(), w.getWorkOrderStatusId().getId()));
            if (q.getResultList() != null) {

                if (!q.getResultList().isEmpty()) {
                    Date time = (Date) q.getResultList().get(0);
                    return time;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
    
    

    public String getQueueTypeName(int i, QueueType queueTypeId) {
        String qry = "select name from queue_type where id=%d and owner_id=%d";
        qry = String.format(qry, queueTypeId.getId(),i);
        Query q = getEntityManager().createNativeQuery(qry);
        return (String)q.getSingleResult();

    }

}


//~ Formatted by Jindent --- http://www.jindent.com
