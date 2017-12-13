
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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;

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
        Query q = getEntityManager().createNativeQuery(String.format(qry, district, from, to), WorkOrder.class);
        return q.getResultList();
    }

    public String getCustomerName(WorkOrder w) {
        String name;

        try {
            if (w.getReferenceType() != null && w.getReferenceTypeData() != null && w.getReferenceType().equals("Billing ID") && !w.getReferenceTypeData().equals("Not Applicable")) {
                String qry = "select name from esbdb.customer where account_number='%s' order by customer_id desc limit 1";
                String qryv = String.format(qry, w.getReferenceTypeData());
                Query q = getEntityManager().createNativeQuery(qryv);
                String fo = (String) q.getSingleResult();
                name = fo == null ? w.getReportedBy() : fo;
            } else {
                name = w.getReportedBy();
            }
        } catch (NoResultException e) {
            name = w.getReportedBy();
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
        qry = String.format(qry, queueTypeId.getId(), i);
        Query q = getEntityManager().createNativeQuery(qry);
        return (String) q.getSingleResult();

    }

    public WorkOrderTemp getEnumWorkOrderByToken(String token) {
        String qry = String.format("select * from work_order_temp where token = '%s'", token);
        Query q = getEntityManager().createNativeQuery(qry, WorkOrderTemp.class);
        return (WorkOrderTemp) q.getSingleResult();
    }

     public QueueType getQueueTypeByQueueNameQueueTypeName(int queueName, int queueTypeName) {
        String sql = String.format( "select * from queue_type where queue_id=(select id from queue where name='%s') and name='%s' ", queueName,queueTypeName);
        return (QueueType)getEntityManager().createNativeQuery(sql,QueueType.class);
    }
    
    public void approveEnumWorkOrder(WorkOrderTemp wot) {
       QueueType qt =  getQueueTypeByQueueNameQueueTypeName(wot.getQueueId(),wot.getQueueTypeId());
       int ticketId = this.createWorkOrder(wot, qt);
       if(ticketId!=0){
           wot.setTicketId(ticketId);
           wot.setCurrentStatus("OPEN");
           wot.setToken(wot.getToken());
           getEntityManager().merge(wot);
       }
    }
    
    public int createWorkOrder(WorkOrderTemp wot,QueueType qt){
        WorkOrder wo = new WorkOrder();
        wo.setBusinessUnit(wot.getBusinessUnit());
        wo.setAddressLine1(wot.getAddressLine1());
        wo.setAddressLine2(wot.getAddressLine2());
        wo.setQueueId(qt.getQueueId());
        wo.setQueueTypeId(qt);
        wo.setContactNumber(wot.getContactNumber());
        wo.setCustomerName(wot.getCustomerName());
        wo.setOwnerId(wot.getOwnerId());
        wo.setReportedBy(wot.getReportedBy());
        wo.setCreateTime(wot.getCreateTime());
        wo.setCurrentStatus(wo.getCurrentStatus());
        wo.setCustomerTariff(wot.getCustomerTariff());
        wo.setCity(wot.getCity());
        
        EntityManager em = getEntityManager();
        em.persist(wo);
        em.flush();
        return wo.getTicketId();
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
