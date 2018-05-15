
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.entity.WorkOrderRemark;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {

    @Autowired
    private WorkOrderTempDap temp;
    @Autowired
    private WorkOrderRemarkDao wora;

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

    public Users findUserById(int id) {
        String query = String.format("select * from users where id=?1");
        List<Users> options = getEntityManager().createNativeQuery(query, Users.class).setParameter(1,
                id).getResultList();

        if ((options != null) && !options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public WorkOrder findByTicketId(int id) {
        String query = String.format("select * from work_order where ticket_id=?1");
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
        String qry = "select * from work_order where is_active=1 and business_unit='%s' and date(create_time) >= date('%s') and date(create_time) <= date('%s')";
        Query q = getEntityManager().createNativeQuery(String.format(qry, district, from, to), WorkOrder.class);
        return q.getResultList();
    }

    public List<WorkOrder> getWorkOrderByParams(String district, String from, String to, String queueTypeIds, String tariffs) {
        String qry = "select * from work_order where is_active=1 and business_unit='%s' and date(create_time) >= date('%s') and date(create_time) <= date('%s') "
                + "and queue_type_id in ("+queueTypeIds+") and customer_tariff in ("+tariffs+")";
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
        StringBuilder sb = new StringBuilder("select ifnull(count(*),0) from work_order where queue_id =  17");
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
        List<QueueType> qt = getEntityManager().createNativeQuery(String.format("select * from queue_type where queue_id = %d", id),
                QueueType.class).getResultList();
        List<QueueTypeData> qs = new ArrayList();
        for (QueueType qtd : qt) {
            QueueTypeData qp = new QueueTypeData();
            qp.setQueueTypeId(qtd.getId());
            qp.setQueueTypeName(qtd.getName());
            qs.add(qp);
        }
        return qs;
    }

    public BigInteger getBarWidgetData(int queueTypeId, String conName, String district) {
        StringBuilder sb = new StringBuilder(String.format("select ifnull(count(*),0)"
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

    public List<BigInteger> getTotalCount() {
        return (List<BigInteger>) getEntityManager().createNativeQuery("select ifnull(count(*),0) from work_order where "
                + "current_status = 'OPEN' or current_status= 'CLOSED' group by current_status").getResultList();

    }

    public WorkOrderTemp getEnumWorkOrderByTicketId(int ticketId) throws WfmWorkerException {
        WorkOrderTemp wot = null;
        try {
            wot = (WorkOrderTemp) getEntityManager().
                    createNativeQuery(String.
                            format("select * from work_order_temp where ticket id = %d",
                                    ticketId), WorkOrderTemp.class).getSingleResult();

        } catch (NoResultException nre) {
            throw new WfmWorkerException("workorder ticket id doesnt exist ");
        }
        return wot;
    }

    public QueueType getEmccConfigDisconnectQueueTypeAndQueue() {
        QueueType qt = null;

        try {
            qt = (QueueType) getEntityManager().
                    createNativeQuery("select * from queue_type where token=(select config_value "
                            + "from emcc_config where config_key='disconnect_queue_type')",
                            QueueType.class).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
        return qt;

    }

    public int createWorkOrder(QueueType qt, String string, String string0, String businessUnit, String summary, String description, String phone, String city, String address, String tarriff, String billingID, String emcc, String string1, String string2, String reportedBy, String customername) {
        WorkOrder wo = new WorkOrder();
        wo.setBusinessUnit(businessUnit);
        wo.setAddressLine1(address);
        wo.setAddressLine2(address);
        wo.setQueueId(qt.getQueueId());
        wo.setQueueTypeId(qt);
        wo.setTicketId(ticketCount());
        wo.setContactNumber(phone);
        wo.setCustomerName(customername);
        wo.setOwnerId(1);
        wo.setDescription(description);
        wo.setReportedBy(reportedBy);
        wo.setCreateTime(new Date());
        wo.setCurrentStatus("OPEN");
        wo.setCustomerTariff(tarriff);
        wo.setCity(city);
        wo.setPriority("Low");
        wo.setReferenceType("Billing ID");
        wo.setReferenceTypeData(billingID);
        wo.setState("Lagos");
        wo.setSummary(summary);
        wo.setToken(RandomStringUtils.randomAlphanumeric(30));
        wo.setChannel("EMCC");

        WorkOrder w = save(wo);
        return w.getTicketId();
    }

    public void addRemark(String emcc, String ticketId, String comment, String string) {
        WorkOrderRemark wor = new WorkOrderRemark();
        wor.setComment(comment);
        wor.setChannel(emcc);
        wor.setToken(RandomStringUtils.randomAlphanumeric(30));
        WorkOrder w = findByTicketId(Integer.parseInt(ticketId));
        wor.setWorkOrderId(w);
        w.getWorkOrderRemarkList().add(wor);
        wor.setCreateTime(new Date());
        wor.setCreatedBy(findUserById(1));
        wor.setCreatedByName(findUserById(1).getFirstname());
        wora.save(wor);
    }

    public List<WorkOrder> getLastWorkOrderinQueueType(String billingId, Integer queueTypeId) {

        String sql = String.format("select * from work_order where reference_type_data = '%s'  and (current_status != '%s' or is_closed = %d) and queue_type_id = %d order by id desc limit 1", billingId,
                "CLOSED", 0, queueTypeId);
        return getEntityManager().createNativeQuery(sql, WorkOrder.class).getResultList();

    }

    private Object getUniqueWorkOrderToken() {
        String token = RandomStringUtils.randomAlphanumeric(30);
        try {
            WorkOrder o = (WorkOrder) getEntityManager().
                    createNativeQuery(String.format("select * from work_order where token='%s'", token, WorkOrder.class)).getSingleResult();
            if (o != null) {
                return getUniqueWorkOrderToken();
            } else {
                return token;
            }
        } catch (NoResultException no) {
            return null;
        }
    }

    public void getData(Map<String, String> map, List<QueueTypeData> qtees, String con, String dis) {

        if (con != null) {
            for (int q = 0; q < qtees.size(); q++) {
                QueueTypeData qt = qtees.get(q);
                String sql = String.format("select count(*)from work_order "
                        + "where queue_type_id = %d and reported_by = '%s' ", qt.getQueueTypeId(), con);

                BigInteger count = (BigInteger) this.getEntityManager().createNativeQuery(sql).getSingleResult();

                map.put(qt.getQueueTypeName(), count.toString());
            }

        } else {
            for (int q = 0; q < qtees.size(); q++) {
                QueueTypeData qt = qtees.get(q);
                String sql = String.format("select count(*)from work_order "
                        + "where queue_type_id = %d and business_unit = '%s' ", qt.getQueueTypeId(), dis);
                BigInteger count = (BigInteger) this.getEntityManager().createNativeQuery(sql).getSingleResult();

                map.put(qt.getQueueTypeName(), count.toString());
            }
        }

    }

    public BigInteger auditCount() {
        return (BigInteger) getEntityManager().createNativeQuery("select count(*) from audit").getSingleResult();
    }

    public List<WorkOrder> findNonMigratedWorkOrders() {
        String sql = "select * from work_order where queue_type_id = 41 and current_status not like 'COMPLETE%'";
        return getEntityManager().createNativeQuery(sql, WorkOrder.class).getResultList();
    }

    public int createWorkOrder(WorkOrderMessage worder) {
        QueueType qt = getQueueTypeByID(worder.getQueueTypeId());
        return createWorkOrder(qt,
                "",
                "1",
                worder.getBusinessUnit(),
                worder.getSummary(),
                worder.getDescription(),
                worder.getContactNumber(),
                worder.getCity(),
                worder.getAddressLine1(),
                worder.getCustomerTariff(),
                worder.getBillingID(),
                "EMCC",
                "",
                "",
                worder.getReportedBy(),
                worder.getCustomerName());
    }

}




//~ Formatted by Jindent --- http://www.jindent.com
