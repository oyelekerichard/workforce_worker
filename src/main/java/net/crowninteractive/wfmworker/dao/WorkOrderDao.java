
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {

    @Autowired
    private WorkOrderTempDap temp;

    public int ticketCount() {
        Integer max = (Integer) getEntityManager()
                .createNativeQuery("select max(ticket_id) from work_order").getSingleResult();
        return max.intValue() + 1;
    }

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
        if (w.getIsClosed() != null) {
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
        return null;
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

    public QueueType getQueueTypeByID(int queueTypeId) {
        String sql = String.format("select * from queue_type where id=%d ", queueTypeId);
        System.out.println(sql);
        return (QueueType) getEntityManager().createNativeQuery(sql, QueueType.class).getSingleResult();
    }

    public void approveEnumWorkOrder(WorkOrderTemp wot) {
        QueueType qt = getQueueTypeByID(wot.getQueueTypeId());
        int ticketId = this.createWorkOrder(wot, qt);
        if (ticketId != 0) {
            wot.setTicketId(ticketId);
            wot.setCurrentStatus("OPEN");
            wot.setToken(wot.getToken());
            temp.edit(wot);
        }
    }

    @Transactional
    public int createWorkOrder(WorkOrderTemp wot, QueueType qt) {
        WorkOrder wo = new WorkOrder();
        wo.setBusinessUnit(wot.getBusinessUnit());
        wo.setAddressLine1(wot.getAddressLine1());
        wo.setAddressLine2(wot.getAddressLine2());
        wo.setQueueId(qt.getQueueId());
        wo.setQueueTypeId(qt);
        wo.setTicketId(ticketCount());
        wo.setContactNumber(wot.getContactNumber());
        wo.setCustomerName(wot.getCustomerName());
        wo.setOwnerId(wot.getOwnerId());
        wo.setReportedBy(wot.getReportedBy());
        wo.setCreateTime(new Date());
        wo.setCurrentStatus("OPEN");
        wo.setCustomerTariff(wot.getCustomerTariff());
        wo.setCity(wot.getCity());
        wo.setPriority(wot.getPriority());
        wo.setReferenceType(wot.getReferenceType());
        wo.setReferenceTypeData(wot.getReferenceTypeData());
        wo.setState(wot.getState());
        wo.setSummary(wot.getSummary());
        wo.setToken(wot.getToken());
        wo.setSlot(wot.getSlot());

        WorkOrder w = save(wo);
        return w.getTicketId();
    }

    public BigInteger getWorkOrderByStatusAndDistrict(String status, String district, String reportedBy) {
        StringBuilder sb = new StringBuilder("select count(*) from work_order where queue_id =  17");
        if (status != null) {
            sb.append(String.format(" and current_status = '%s'", status));
        }

        if (district != null) {
            sb.append(String.format(" and business_unit = '%s'", district));
        }

        if (reportedBy != null) {
            sb.append(String.format(" and reported_by = '%s'", reportedBy));
        }

        try {
            BigInteger bd = (BigInteger) this.getEntityManager().createNativeQuery(sb.toString()).getSingleResult();
            return bd;
        } catch (NoResultException no) {
            return BigInteger.ZERO;
        }

    }

    public List<QueueTypeData> getQueueTypesByQueue(int id) {
        List<QueueType> qt = getEntityManager().createNativeQuery
        (String.format("select * from queue_type where queue_id = %d", id),
                QueueType.class).getResultList();
        List<QueueTypeData> qs = new ArrayList();
        for(QueueType qtd : qt){
            QueueTypeData qp = new QueueTypeData();
            qp.setQueueTypeId(qtd.getId());
            qp.setQueueTypeName(qtd.getName());
            qs.add(qp);
        }
        return qs;
    }

   
   
    public BigInteger getBarWidgetData(int queueTypeId, String conName, String district) {
        StringBuilder sb = new 
        StringBuilder(String.format("select count(*)"
                + " from work_order where queue_type_id = %d", queueTypeId));
   

        if (district != null) {
            sb.append(String.format(" and business_unit = '%s'", district));
        }

        if (conName != null) {
            sb.append(String.format(" and reported_by = '%s'", conName));
        }

        try {
            BigInteger bd = (BigInteger) this.getEntityManager().createNativeQuery(sb.toString()).getSingleResult();
            return bd;
        } catch (NoResultException no) {
            return BigInteger.ZERO;
        }
    }
    
    
    public List<Object[]> getTotalCount(){
      return (List<Object[]>)getEntityManager().createNativeQuery("select count(*) from work_order where "
               + "current_status = 'OPEN' or current_status= 'CLOSED' group by current_status").getResultList();
        
    }

}




//~ Formatted by Jindent --- http://www.jindent.com
