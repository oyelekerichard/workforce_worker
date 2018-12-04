
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.google.common.base.Optional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import net.crowninteractive.wfmworker.entity.Engineer;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.RequestEnumerationBody;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderExtra;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.entity.WorkOrderRemark;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.EnumerationRequestModel;
import net.crowninteractive.wfmworker.misc.EnumerationWorkOrderDownloadModel;
import net.crowninteractive.wfmworker.misc.WorkOrderEnumerationBody;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author osita
 */
@Component
public class WorkOrderDao extends AbstractDao<Integer, WorkOrder> {

    private final Logger logger = Logger.getLogger("DaoLogger");

    @Autowired
    private WorkOrderTempDao temp;
    @Autowired
    private EnumerationWorkOrderDao ewod;
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
                return w.getClosedTime() == null ? null : DateFormatUtils.format(w.getClosedTime(), "yyyy-MM-dd HH:mm:SS");
            } else if (w.getCurrentStatus().toLowerCase().equals("completed") || w.getCurrentStatus().toLowerCase().equals("resolved")) {
                if (w.getWorkOrderStatusId() == null) {
                    return null;
                }
                Query q = getEntityManager().createNativeQuery("select DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%s') from work_order_update  where work_order_id=?1 and "
                        + "work_order_status_id=?2 order by id desc limit 1").
                        setParameter(1, w.getId()).
                        setParameter(2, w.getWorkOrderStatusId().getId());
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

    public String get(int i, QueueType queueTypeId) {
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
        String sql = String.format("select * from queue_type where id='%s' ", queueTypeId);
        System.out.println(sql);
        return (QueueType) getEntityManager().createNativeQuery(sql, QueueType.class).getSingleResult();
    }

    public EnumerationWorkOrder getEnumerationWorkOrder(String tempToken) {
        String sql = String.format("select * from enumeration_work_order where work_order_temp_token='%s' ", tempToken);
        System.out.println(sql);
        return (EnumerationWorkOrder) getEntityManager().createNativeQuery(sql, EnumerationWorkOrder.class).getSingleResult();
    }

    public void approveEnumWorkOrder(WorkOrderTemp wot) {
        QueueType qt = getQueueTypeByID(wot.getQueueTypeId());
        EnumerationWorkOrder enumerationWorkOrder = getEnumerationWorkOrder(wot.getToken());
        int ticketId = this.createWorkOrder(wot, qt);

        //update work_order
        if (ticketId != 0) {
            logger.info("-----------Ticket Id Generated for Record -----------------" + ticketId);
            wot.setTicketId(ticketId);
            wot.setCurrentStatus("OPEN");
            wot.setToken(wot.getToken());
            enumerationWorkOrder.setWork_order_id(ticketId + "");
            enumerationWorkOrder.setWork_order_temp_token(wot.getToken());
            ewod.edit(enumerationWorkOrder);
            temp.delete(wot);
            logger.info("-----------deleting enumeration record -----------------" + wot.getId());
            logger.info("-----------Ticket Id set for Records -----------------" + enumerationWorkOrder.getWork_order_id());
        }
    }

    @Transactional
    public int createWorkOrder(WorkOrderTemp wot, QueueType qt) {
        
        logger.info("-----------Queue-----------------" + qt.getQueueId());
        logger.info("-----------Queue Type -----------------" + qt.getId());
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

//        wo.setCurrentBill(wot.getCurrentBill());
//        wo.setLastPaymentAmount(wot.getLastPaymentAmount());
//        wo.setLastPaymentDate(wot.getLastPaymentDate());
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

    public int createWorkOrder(QueueType qt, String string, String string0, String businessUnit, String summary, String description, String phone, String city, String address, String tarriff, String billingID, String emcc, String string1, String string2, String reportedBy, String customername, String amount, String currentBill, String lastPaidAmount, Date lastPaymentDate) {
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
        wo.setDebtBalanceAmount(Double.valueOf(amount));
        wo.setIsAssigned(Short.valueOf("0"));
        wo.setIsClosed(Short.valueOf("0"));
//        wo.setCurrentBill(currentBill);
//        wo.setLastPaymentAmount(Double.valueOf(lastPaidAmount));
//        wo.setLastPaymentDate(lastPaymentDate);

        WorkOrder w = save(wo);
        return w.getTicketId();

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

        String sql = " select * from work_order where queue_type_id in ((select config_value from config where config_key= 'metering_plan_queue_type'),(select config_value from config where config_key= 'installation_queue_type')) and current_status not like 'INSTALLATION_COMPLETED'";
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
                worder.getCustomerName(), "0",
                worder.getCurrentBill(),
                worder.getLastPaymentAmount(),
                worder.getLastPaymentDate());
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

    public WorkOrderEnumerationBody findByTicketIdEnum(Integer ticketId) {
        List<WorkOrder> resultList = getEntityManager().
                createNativeQuery("select * from work_order where ticket_id = ? ", WorkOrder.class).
                setParameter(1, ticketId)
                .getResultList();
        List<EnumerationWorkOrder> ewos = getEntityManager().
                createNativeQuery("select * from enumeration_work_order where work_order_id = ? ", EnumerationWorkOrder.class).
                setParameter(1, ticketId)
                .getResultList();
        if (resultList != null && ewos != null) {
            if (resultList.size() > 0 && ewos.size() > 0) {
                return new WorkOrderEnumerationBody(resultList.get(0), ewos.get(0));
            }
        }
        return new WorkOrderEnumerationBody();
    }

    public List<EnumerationWorkOrderDownloadModel> getEnumerationDownloadList(String sql, String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {
           
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
        if (reportedBy != null) {
            sql += String.format("and reported_by ='%s'", reportedBy);
        }
        
        logger.info("Compiled SQL " + sql);
        List<EnumerationWorkOrderDownloadModel> model = new ArrayList();
        //initialize count
        BigInteger val = null;
        List<Object[][]> list = getEntityManager().
                createNativeQuery(sql).getResultList();
        for (Object[] e : list) {            
            logger.info("Requests " + e[1]);            
            EnumerationWorkOrderDownloadModel enumReq = new EnumerationWorkOrderDownloadModel(e);
            model.add(enumReq);                   
        }       
        return model;
    }
    
    public Map.Entry<BigInteger, List<EnumerationRequestModel.RequestListModel>> getEnumerationList(String sql, String district, String from, String to, Integer page, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {
        page = (page - 1) * 1000;
           
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
        
        final String sql2 = sql + " limit 1000 offset " + page;
        
        logger.info("Compiled SQL " + sql2);
        List<EnumerationRequestModel.RequestListModel> model = new ArrayList();
        //initialize count
        BigInteger val = null;
        List<Object[][]> list = getEntityManager().
                createNativeQuery(sql2).getResultList();
        for (Object[] e : list) {
            
            //logger.info("Enumeration List -------->" + e[1]);
            
            EnumerationRequestModel enumReqs = new EnumerationRequestModel();
            
            // Instantiating the inner class           
            EnumerationRequestModel.RequestListModel m = enumReqs.new RequestListModel(e);
            model.add(m);            
            // get count
            Query query = getEntityManager().createNativeQuery(String.format("select count(*) from (%s) as new", sql));  
            val = (BigInteger) query.getSingleResult();            
        }
        
        return new DefaultMapEntry<>(val, model);
    }
    
    public Object[] getEnumerationReport(String district, String fromDate, String toDate) {
        
        String sql1 = " select count(*) from work_order w join enumeration_work_order e on w.ticket_id = e.work_order_id  "
                + "where w.queue_id = (select id from queue where name like '%enumeration%') and w.current_status != 'Obsolete'";
        String sql2 = "select count(*) from work_order_temp ";
        boolean isFirst = true;

        if (district != null) {

            sql1 = sql1.concat(String.format(" and business_unit = '%s'", district));

            if (isFirst) {
                sql2 = sql2.concat(String.format(" where business_unit = '%s'", district));
            } else {
                sql2 = sql2.concat(String.format(" and business_unit = '%s'", district));
            }
            isFirst = false;
        }
        if (fromDate != null && toDate != null) {
            sql1 = sql1.concat(String.format(" and create_time between '%s' and '%s' ", fromDate, toDate));

            if (isFirst) {
                sql2 = sql2.concat(String.format(" where create_time between '%s' and '%s' ", fromDate, toDate));
            } else {
                sql2 = sql2.concat(String.format(" and create_time between '%s' and '%s' ", fromDate, toDate));
            }
            isFirst = false;
        }
        
        // get count
        BigInteger enum1 = (BigInteger) getEntityManager().createNativeQuery(sql1).getSingleResult(); 
        BigInteger enum2 = (BigInteger) getEntityManager().createNativeQuery(sql2).getSingleResult();

        return new Object[]{enum1, enum2};
    }
    
    public RequestEnumerationBody getEnumRequestByToken(String token) {
       
        final String sql = String.format("SELECT `id`,customer_tariff, ticket_id, "
                + "(select name from queue where id=wt.queue_id) as queue_id,"
                + "(select name from queue_type where id=wt.queue_type_id) "
                + "as queue_type_id, `summary`, `description`, `contact_number`,"
                + " `reference_type`, `reference_type_data`, `address_line_1`, "
                + "`city`, `state`, `business_unit`, `priority`, `create_time`,"
                + " `channel`, `is_active`, `current_status`, `reported_by`,  `customer_name`, "
                + "`disco`, `sub_disco`, `injection_substation`, "
                + "`injection_substation_name`, `power_transformer`, `power_transformer_name`, "
                + "`feeder`, `feeder_name`, `ht_pole`, `high_tension_physical_id`, `distribution_substation`, "
                + "`distribution_substation_name`, `upriser`, `service_pole`, `service_wire`, "
                + "`nerc_id`, `connection_type`, `transformer`, `token` "
                + " FROM `work_order_temp` wt "
                + " where token = '%s' ", token);
      
        
        logger.info("Compiled SQL " + sql);
        
        try {
             
            List<EnumerationRequestModel> model = new ArrayList();
//            Object[] e = (Object[]) getEntityManager().createNativeQuery(sql).getSingleResult();
             List<Object[][]> list = getEntityManager().
                createNativeQuery(sql).getResultList();
            for (Object[] e : list) {
                EnumerationRequestModel m = new EnumerationRequestModel();
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
                m.setToken((String) e[38]);
                
                model.add(m);
            }
          // add Enumeration data
            List<EnumerationWorkOrder> ewos = getEntityManager().
                createNativeQuery("select * from enumeration_work_order where work_order_temp_token = ? ", EnumerationWorkOrder.class).
                setParameter(1, token)
                .getResultList();
            
            if (model != null && ewos != null) {
                if (model.size() > 0 && ewos.size() > 0) {
                    return new RequestEnumerationBody(model.get(0), ewos.get(0));
                }
            }
            
        } catch (NoResultException ex) {
             ex.printStackTrace();
            return null;
        }       
        return null;
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

    public Integer createWorkOrder(QueueType qt, RequestObj r) {
        WorkOrder.WorkOrderBuilder builder = new WorkOrder.WorkOrderBuilder();
        builder.setAddressLine1(r.getAddress()).setBusinessUnit(r.getBusinessUnit()).setAmount(Double.valueOf(r.getAmount()))
                .setCity(r.getCity()).setContactNumber(r.getPhone()).setCurrentBill(Double.valueOf(r.getCurrentBill()))
                .setDescription(r.getDescription()).setDueDate(r.getDueDate())
                .setLastPaymentAmount(Double.valueOf(r.getLastPaidAmount())).setLastPaymentDate(r.getLastPaymentDate())
                .setPreviousOutstanding(r.getPreviousOutstanding()).setClosed(Short.valueOf("0")).setActive(1)
                .setPurpose(r.getPurpose()).setReportedBy(r.getReportedBy()).setSummary(r.getSummary()).setQueueType(qt)
                .setCreateTime(new Date()).setCurrentStatus("OPEN").setPriority("Low").setReferenceType("Billing ID")
                .setState("Lagos").setChannel("EMCC").setTariff(r.getTariff()).setBillingId(r.getBillingId()).setName(r.getName())
                .setQueue(qt.getQueueId()).setToken(RandomStringUtils.randomAlphanumeric(30)).setDebtBalanceAmount(0.0).setTicketId(ticketCount());

        Integer found = Optional.fromNullable(r.getStaffId()).isPresent() ? getEngineerIdByStaffId(r.getStaffId()) : getEngineerIdByBook(r.getAccountNumber(), qt.getId());

        if (found != null) {
            builder.setEngineerId(new Engineer(found));
            builder.setAssigned(Short.valueOf("1"));
            builder.setDateAssigned(new Date());
            builder.setWorkDate(new Date());

        }

        builder.setOwnerId(1);
        WorkOrder build = builder.build();
        return save(build).getTicketId();

    }

    private void updateStaffCode(Integer start, String staffCode) {
        String sql = "update users set staff_code = ? where id = ?";
        getEntityManager().
                createNativeQuery(sql).
                setParameter(1, staffCode).
                setParameter(2, start);

    }

    public Integer getEngineerIdByStaffId(String staffId) {
        System.out.println(">>>>>>>>>getting engineer by staff id >>>>>>>>>."+staffId);
        String query = "select id from engineer where user_id in (select id from users where staff_id = ?) ";
        List<Integer> engineerId = getEntityManager().createNativeQuery(query).setParameter(1,
                Integer.parseInt(staffId)).getResultList();
        return engineerId.isEmpty() ? null : engineerId.get(0);
    }

    public Integer getEngineerIdByBook(String an, Integer queueTypeId) {
        System.out.println(">>>>>>>>getting engineer by  acconutnumber "+an);
        String query = "select id from engineer where book_number like ? limit 1";
        List<Integer> engineerId = getEntityManager().createNativeQuery(query).setParameter(1,
                "%" + StringUtils.substring(an, 0, 6) + "%").getResultList();
        System.out.println(">>>>>>>>>>>>>ENgineer  book >>>>>>"+ StringUtils.substring(an, 0, 6));
        if (engineerId.isEmpty()) {
            System.out.println("------no engineer within book number --------");
            return null;
        }
        String query2 = "select * from queue_type where id = ? and auto_assign_resource_to_book = ?";
        List<QueueType> qts = getEntityManager().createNativeQuery(query2, QueueType.class).setParameter(1,
                queueTypeId).setParameter(2, 1).getResultList();
        if (qts.isEmpty()) {
            System.out.println("-----------queue  type not enabled for auto assign to resouce ");
            return null;
        }
        return engineerId.get(0);
    }

}




//~ Formatted by Jindent --- http://www.jindent.com
