
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrderExtra;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.entity.WorkOrderRemark;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.WorkOrderDownloadModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {

    private final Logger logger = Logger.getLogger("DaoLogger");

    @Autowired
    private WorkOrderTempDap temp;
    @Autowired
    private WorkOrderRemarkDao wora;
    @Autowired
    private WorkOrderExtraDao wdao;
    @Autowired
    private UsersDao udao;

    public int ticketCount() {
        Integer max = (Integer) getEntityManager()
                .createNativeQuery("select max(ticket_id) from work_order").getSingleResult();
        return max.intValue() + 1;
    }
    
      public int lastTicket() {
        Integer max = (Integer) getEntityManager()
                .createNativeQuery("select max(ticket_id) from work_order").getSingleResult();
        return max.intValue();
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
                + "and queue_type_id in (" + queueTypeIds + ")";
        if (tariffs.length() > 0) {
            qry.concat(" and customer_tariff in (" + tariffs + ")");
        }
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

    public String getDateResolved(WorkOrder w) {
        if (w.getIsClosed() != null) {
            if (w.getIsClosed() == 1) {
                return DateFormatUtils.format(w.getClosedTime(), "yyyy-MM-dd HH:mm:SS");
            } else if (w.getCurrentStatus().toLowerCase().equals("completed") || w.getCurrentStatus().toLowerCase().equals("resolved")) {
                if (w.getWorkOrderStatusId() == null) {
                    return null;
                }
                Query q = getEntityManager().createNativeQuery(
                        String.format("select create_time from work_order_update  where work_order_id=%d and "
                                + "work_order_status_id=%d order by id desc limit 1", w.getId(), w.getWorkOrderStatusId().getId()));
                if (q.getResultList() != null) {

                    if (!q.getResultList().isEmpty()) {
                        String time = (String) q.getResultList().get(0);
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
            temp.delete(wot);
            logger.info("-----------deleting enumeration record -----------------" + wot.getId());
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
        wo.setDebtBalanceAmount(Double.valueOf(0));

        WorkOrder w = save(wo);
        WorkOrderExtra woe = new WorkOrderExtra();
        woe.setConnectionType(wot.getConnectionType());
        woe.setTransformer(wot.getTransformer());
        woe.setDisco(wot.getDisco());
        woe.setDistributionSubstation(wot.getDistributionSubstation());
        woe.setDistributionSubstationName(wot.getDistributionSubstationName());
        woe.setFeeder(wot.getFeeder());
        woe.setFeederName(wot.getFeederName());
        woe.setHighTensionPhysicalId(wot.getHighTensionPhysicalId());
        woe.setHtPole(wot.getHtPole());
        woe.setInjectionSubstation(wot.getInjectionSubstation());
        woe.setInjectionSubstationName(wot.getInjectionSubstationName());
        woe.setNercId(wot.getNercId());
        woe.setPowerTransformer(wot.getPowerTransformer());
        woe.setPowerTransformerName(wot.getPowerTransformerName());
        woe.setServicePole(wot.getServicePole());
        woe.setServiceWire(wot.getServiceWire());
        woe.setSubDisco(wot.getSubDisco());
        woe.setUpriser(wot.getUpriser());

        woe.setId(w);
        wdao.save(woe);
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

    public int createWorkOrder(QueueType qt,
            String string,
            String string0, 
            String businessUnit, 
            String summary,
            String description,
            String phone, 
            String city, 
            String address, 
            String tarriff, 
            String billingID,
            String emcc, 
            String string1, 
            String string2, 
            String reportedBy,
            String customername, 
            String amount) {
        String sql = String.format("insert into work_order (ticket_id,token,owner_id,queue_id,"
                        + "queue_type_id,summary,description,contact_number,reference_type,reference_type_data,"
                        + "address_line_1,address_line_2,city,state,business_unit,customer_tariff,priority,"
                        + "create_time,channel,update_time,reported_by,"
                        + "customer_name,debt_balance_amount) "
                        + " select max(ticket_id)+1,'%s',%d,%d,%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',"
                        + "'%s','%s',now(),'%s',now(),'%s','%s',%.2f from work_order ",RandomStringUtils.randomAlphanumeric(30),1,
                        qt.getQueueId().getId(),qt.getId(),summary,description,phone,"Billing ID",billingID,address,address,
                        city,"Lagos",businessUnit,tarriff,"Low","EMCC",reportedBy,customername,Double.valueOf(amount));
       
        Session session = this.getSession();
        session.createSQLQuery(sql).executeUpdate();
       
        return lastTicket();
    }

    public WorkOrder createWorkOrderV2(QueueType qt, String string, String string0, String businessUnit, String summary, String description, String phone, String city, String address, String tarriff, String billingID, String emcc, String string1, String string2, String reportedBy, String customername) {
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
        wo.setIsAssigned(Short.valueOf("0"));
        wo.setState("Lagos");
        wo.setSummary(summary);
        wo.setToken(RandomStringUtils.randomAlphanumeric(30));
        wo.setChannel("EMCC");
        wo.setDebtBalanceAmount(Double.valueOf(0));

        WorkOrder w = save(wo);
        return w;
    }

    public void addRemark(String emcc, String ticketId, String comment, String string, Double amount) {
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
        wor.setAmount(amount);
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

        String sql = "select * from work_order where queue_type_id = (select id from queue_type "
                + "where token=(select config_value from emcc_config where config_key="
                + " 'metering_plan_queue_type')) and current_status not like 'COMPLETE%'";
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
                worder.getCustomerName(), "0");
    }

    public WorkOrder createWorkOrder(WorkOrder wo) {
        return save(wo);
    }

    public List<WorkOrder> findByQueueId(Integer queueId, int offset, int count) {
        return getEntityManager().
                createNativeQuery("select * from work_order where queue_id = ? order by id desc limit ?,? ", WorkOrder.class).
                setParameter(1, queueId).
                setParameter(2, offset).
                setParameter(3, count)
                .getResultList();
    }

    public WorkOrder findByQueueTypeId(Integer ticketId) {
        List<WorkOrder> resultList = getEntityManager().
                createNativeQuery("select * from work_order where ticket_id = ? ", WorkOrder.class).
                setParameter(1, ticketId)
                .getResultList();
        if (resultList != null) {
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
        }
        return new WorkOrder();
    }

    public List<WorkOrderDownloadModel> getWorkOrders(String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {
        String sql = "SELECT wt.id ,customer_tariff,ticket_id,"
                + "(select name from queue where id=wt.queue_id) as queue_id,"
                + "(select name from queue_type where id=wt.queue_type_id) "
                + "as queue_type_id, `summary`, `description`, `contact_number`, `reference_type`, `reference_type_data`, `address_line_1`, "
                + "`city`, `state`, `business_unit`, `priority`, `create_time`, `channel`, `is_active`, `current_status`, `reported_by`,  `customer_name`,"
                + "connection_type,disco,distribution_substation,distribution_substation_name,feeder,feeder_name,high_tension_physical_id,ht_pole,injection_substation,"
                + "injection_substation_name,nerc_id,power_transformer,power_transformer_name,service_pole,service_wire,sub_disco,transformer,upriser,"
                + "`created_by` FROM `work_order` wt left join work_order_extra we on wt.id = we.id where business_unit like {unit} and cast(create_time as date) >= cast({from} as date) and cast(create_time as date) <= cast({to} as date )";

        if (to.equals("create_time")) {
            sql = sql.replace("{to}", "create_time");
        }
        if (!to.equals("create_time")) {
            sql = sql.replace("{to}", String.format("'%s'", to));
        }
        if (from.equals("create_time")) {
            sql = sql.replace("{from}", "create_time");
        }
        if (!from.equals("create_time")) {
            sql = sql.replace("{from}", String.format("'%s'", from));
        }
        if (district.equals("business_unit")) {
            sql = sql.replace("{unit}", district);
        }
        if (!district.equals("business_unit")) {
            sql = sql.replace("{unit}", "'district%'".replace("district", district));
        }
        if (queue != null) {
            sql += "and queue_id=(select id from queue where name like 'quet%')".replace("quet", queue);

        }
        if (queueType != null) {
            sql += ("and queue_type_id=(select qt.id from queue_type qt, queue q where qt.name like 'queueName%' and q.name like 'enumeration' and qt.queue_id = q.id)")
                    .replace("queueName", queueType);

        }
        if (status != null) {
            sql += "and current_status like 'statuss%'".replace("statuss", status);
        }
        if (priority != null) {
            sql += "and priority like 'prioritys%'".replace("prioritys", priority);
        }
        if (billingId != null) {
            sql += "and reference_type_data like 'billing%'".replace("billing", billingId);
        }
        if (ticketId != null) {
            sql += String.format("and ticket_id =%s", ticketId);
        }
        if (reportedBy != null) {
            sql += String.format("and reported_by ='%s'", reportedBy);
        }

        sql += " and current_status not like '%OBSOLETE%";

        logger.info("Compiled SQL " + sql);
        List<WorkOrderDownloadModel> model = new ArrayList();
        List<Object[][]> list = getEntityManager().
                createNativeQuery(sql).getResultList();
        for (Object[] e : list) {
            WorkOrderDownloadModel m = new WorkOrderDownloadModel();
            m.setId((Integer) e[0]);
            m.setCustomerTariff((String) e[1]);
            m.setTicketId((Integer) e[2]);
            m.setQueueId((String) e[3]);
            m.setQueueTypeId((String) e[4]);
            m.setSummary((String) e[5]);
            m.setDescription((String) e[6]);
            m.setContactNumber((String) e[7]);
            m.setReferenceType((String) e[8]);
            m.setReferenceTypeData((String) e[9]);
            m.setAddressLine1((String) e[10]);
            m.setCity((String) e[11]);
            m.setState((String) e[12]);
            m.setBusinessUnit((String) e[13]);
            m.setPriority((String) e[14]);
            m.setCreateTime((Timestamp) e[15]);
            m.setChannel((String) e[16]);
            m.setIsActive((Integer) e[17]);
            m.setCurrentStatus((String) e[18]);
            m.setReportedBy((String) e[19]);
            m.setCustomerName((String) e[20]);
            m.setConnectionType((String) e[21]);
            m.setDisco((String) e[22]);
            m.setDistributionSubstation((String) e[23]);
            m.setDistributionSubstationName((String) e[24]);
            m.setFeeder((String) e[25]);
            m.setFeederName((String) e[26]);
            m.setHighTensionPhysicalId((String) e[27]);
            m.setHtPole((String) e[28]);
            m.setInjectionSubstation((String) e[29]);
            m.setInjectionSubstationName((String) e[30]);
            m.setNercId((String) e[31]);
            m.setPowerTransformer((String) e[32]);
            m.setPowerTransformerName((String) e[33]);
            m.setServicePole((String) e[34]);
            m.setServiceWire((String) e[35]);
            m.setSubDisco((String) e[36]);
            m.setTransformer((String) e[37]);
            m.setUpriser((String) e[38]);
            m.setCreatedBy((Integer) e[39]);

            model.add(m);
        }

        return model;
    }

    public List<WorkOrderDownloadModel> getRequests(String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {

        String sql = "SELECT `id`,customer_tariff,ticket_id,"
                + "(select name from queue where id=wt.queue_id) as queue_id,"
                + "(select name from queue_type where id=wt.queue_type_id) "
                + "as queue_type_id, `summary`, `description`, `contact_number`,"
                + " `reference_type`, `reference_type_data`, `address_line_1`, "
                + "`city`, `state`, `business_unit`, `priority`, `create_time`,"
                + " `channel`, `is_active`, `current_status`, `reported_by`,  `customer_name` "
                + "`disco`, `sub_disco`, `injection_substation`, "
                + "`injection_substation_name`, `power_transformer`, `power_transformer_name`, "
                + "`feeder`, `feeder_name`, `ht_pole`, `high_tension_physical_id`, `distribution_substation`, "
                + "`distribution_substation_name`, `upriser`, `service_pole`, `service_wire`, "
                + "`nerc_id`, `connection_type`, `transformer`, `created_by` "
                + "FROM `work_order_temp` wt where business_unit like {unit} "
                + "and cast(create_time as date) >= cast({from} as date) "
                + "and cast(create_time as date) <= cast({to} as date )";

        if (to.equals("create_time")) {
            sql = sql.replace("{to}", "create_time");
        }
        if (!to.equals("create_time")) {
            sql = sql.replace("{to}", String.format("'%s'", to));
        }
        if (from.equals("create_time")) {
            sql = sql.replace("{from}", "create_time");
        }
        if (!from.equals("create_time")) {
            sql = sql.replace("{from}", String.format("'%s'", from));
        }
        if (district.equals("business_unit")) {
            sql = sql.replace("{unit}", district);
        }
        if (!district.equals("business_unit")) {
            sql = sql.replace("{unit}", "'district%'".replace("district", district));
        }
        if (queue != null) {
            sql += "and queue_id=(select id from queue where name like 'quet%')".replace("quet", queue);

        }
        if (queueType != null) {
            sql += ("and queue_type_id=(select qt.id from queue_type qt, queue q where qt.name like 'queueName%' and q.name like 'enumeration' and qt.queue_id = q.id)")
                    .replace("queueName", queueType);

        }
        if (status != null) {
            sql += "and current_status like 'statuss%'".replace("statuss", status);
        }
        if (priority != null) {
            sql += "and priority like 'prioritys%'".replace("prioritys", priority);
        }
        if (billingId != null) {
            sql += "and reference_type_data like 'billing%'".replace("billing", billingId);
        }
        if (ticketId != null) {
            sql += String.format("and ticket_id =%s", ticketId);
        }
        if (reportedBy != null) {
            sql += String.format("and reported_by ='%s'", reportedBy);
        }

        logger.info("Compiled SQL " + sql);
        List<WorkOrderDownloadModel> model = new ArrayList();
        List<Object[][]> list = getEntityManager().
                createNativeQuery(sql).getResultList();
        for (Object[] e : list) {
            WorkOrderDownloadModel m = new WorkOrderDownloadModel();
            m.setId((Integer) e[0]);
            m.setCustomerTariff((String) e[1]);
            m.setTicketId((Integer) e[2]);
            m.setQueueId((String) e[3]);
            m.setQueueTypeId((String) e[4]);
            m.setSummary((String) e[5]);
            m.setDescription((String) e[6]);
            m.setContactNumber((String) e[7]);
            m.setReferenceType((String) e[8]);
            m.setReferenceTypeData((String) e[9]);
            m.setAddressLine1((String) e[10]);
            m.setCity((String) e[11]);
            m.setState((String) e[12]);
            m.setBusinessUnit((String) e[13]);
            m.setPriority((String) e[14]);
            m.setCreateTime((Timestamp) e[15]);
            m.setChannel((String) e[16]);
            m.setIsActive((Integer) e[17]);
            m.setCurrentStatus((String) e[18]);
            m.setReportedBy((String) e[19]);;
            m.setDisco((String) e[20]);
            m.setSubDisco((String) e[21]);
            m.setInjectionSubstation((String) e[22]);
            m.setInjectionSubstationName((String) e[23]);
            m.setPowerTransformer((String) e[24]);
            m.setPowerTransformerName((String) e[25]);
            m.setFeeder((String) e[26]);
            m.setFeederName((String) e[27]);
            m.setHtPole((String) e[28]);
            m.setHighTensionPhysicalId((String) e[29]);
            m.setDistributionSubstation((String) e[30]);
            m.setDistributionSubstationName((String) e[31]);
            m.setUpriser((String) e[32]);
            m.setServicePole((String) e[33]);
            m.setServiceWire((String) e[34]);
            m.setNercId((String) e[35]);
            m.setConnectionType((String) e[36]);
            m.setTransformer((String) e[37]);
            m.setCreatedBy((Integer) e[38]);

            model.add(m);
        }

        return model;
    }

    public Integer hasNextRecord(Integer start) {
        start = getNextID(start);
        if (start == null) {
            return null;
        }

        String abbr = getInitials(start);
        String staffCode = getLastStaffCode(abbr);

        String lastInSeries = staffCode == null ? "0" : staffCode.substring(2);
        int c = Integer.parseInt(lastInSeries);

        if (c < 9) {
            staffCode = abbr.concat("00").concat(String.valueOf(c + 1));
        } else if (c < 99) {
            staffCode = abbr.concat("0").concat(String.valueOf(c + 1));
        } else {
            staffCode = abbr.concat(String.valueOf(c + 1));
        }
        updateStaffCode(start, staffCode.toUpperCase());
        return start;
    }

    private Integer getNextID(Integer start) {
        try {
            Integer i = (Integer) getEntityManager().createNativeQuery("select id  from users where id > ? limit 1 ").
                    setParameter(1, start).getSingleResult();
            return i;
        } catch (NoResultException e) {
            return null;
        }
    }

    private String getInitials(Integer start) {
        String sql = "select concat(substring(firstname, 1,1), substring(lastname, 1,1)) from users where id = " + start;
        try {
            String nit = (String) getEntityManager().createNativeQuery(sql).getSingleResult();
            return nit;
        } catch (NoResultException noe) {
            return null;
        }
    }

    private String getLastStaffCode(String abbr) {
        abbr = "'".concat(abbr).concat("'");
        String sql = "select staff_code from users where staff_code LIKE concat(" + abbr + ",'%') order by id desc limit 1";
        try {
            String last = (String) getEntityManager().createNativeQuery(sql).getSingleResult();
            return last;
        } catch (NoResultException noe) {
            return null;
        }
    }

    private void updateStaffCode(Integer start, String staffCode) {
        Users u = udao.findById(start);
        u.setStaffCode(staffCode);
        udao.edit(u);
    }

}




//~ Formatted by Jindent --- http://www.jindent.com
