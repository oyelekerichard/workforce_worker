/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Engineer;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderEnumerationBody;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author johnson3yo
 */
@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderDao wdao;
    @Autowired
    private JmsTemplate template;

    @Value(value = "${dev.disconnection.complete.out}")
    private String disconnectionQueue;

    @Autowired
    private ReentrantLock reentrantLock;

    public Awesome addToDisconnectionQueue(String amount, String billingID, String businessUnit, String tarriff, String city, String address, String phone, String summary, String description, String reportedBy, String currentBill, String lastPaidAmount, Date lastPaymentDate) {

        try {

            String customername = null;
            if (billingID != null) {
                Awesome awe = getCustomerDetails(billingID, "a");
                if (awe.getResp() == 0) {
                    Gson gson = new GsonBuilder().create();
                    Map jsonMap = gson.fromJson(gson.toJson(awe.getObject()), Map.class);
                    System.out.println(jsonMap);
                    customername = (String) jsonMap.get("name");
                }
            }

            //fetch queueTypeToken
            QueueType qt = wdao.getEmccConfigDisconnectQueueTypeAndQueue();
            if (qt != null) {
                int ticketId = 0;
                List<WorkOrder> wo = wdao.getLastWorkOrderinQueueType(billingID, qt.getId());
                if (wo != null) {

                    if (wo.size() == 0) {
                        ticketId = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername, amount, currentBill, lastPaidAmount, lastPaymentDate);
                        return StandardResponse.ok(ticketId);
                    } else {
                        WorkOrder wor = wo.get(0);
                        wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), description, "1", Double.valueOf(amount));
                        ticketId = wor.getTicketId();
                        return StandardResponse.ok(ticketId);
                    }

                } else {
                    int tid = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername, amount, currentBill, lastPaidAmount, lastPaymentDate);
                    return StandardResponse.ok(tid);

                }
            } else {
                return StandardResponse.disconnectionQueueTypeNotSet();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Awesome getCustomerDetails(String billingID, String a) {

        try {
            HttpQuery httpQuery = new HttpQuery();
            String resp = httpQuery.getCustomerDetails(billingID, a);
            if (resp != null && !resp.isEmpty()) {
                Awesome b = new Gson().fromJson(resp, Awesome.class);
                return b;
            }
            System.out.print(resp);
            return StandardResponse.errorDuringProcessing();
        } catch (Exception e) {
            e.printStackTrace();
            return StandardResponse.systemError();
        }

    }

    public int createWorkOrder(WorkOrderMessage worder) throws WfmWorkerException {
        return wdao.createWorkOrder(worder);
    }

    public WorkOrder addToDisconnectionQueueV2(String amount, String billingID, String businessUnit, String tarriff, String city, String address, String phone, String summary, String description, String reportedBy) {

        try {

            String customername = null;
            if (billingID != null) {
                Awesome awe = getCustomerDetails(billingID, "a");
                if (awe.getResp() == 0) {
                    Gson gson = new GsonBuilder().create();
                    Map jsonMap = gson.fromJson(gson.toJson(awe.getObject()), Map.class);
                    System.out.println(jsonMap);
                    customername = (String) jsonMap.get("name");
                }
            }

            //fetch queueTypeToken
            QueueType qt = wdao.getEmccConfigDisconnectQueueTypeAndQueue();
            if (qt != null) {

                int ticketId = 0;

                List<WorkOrder> wo = wdao.getLastWorkOrderinQueueType(billingID, qt.getId());
                if (wo != null) {

                    if (wo.size() == 0) {
                        WorkOrder w = wdao.createWorkOrderV2(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername);
                        return w;
                    } else {
                        WorkOrder wor = wo.get(0);
                        wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), description, "1", Double.valueOf(amount));
                        ticketId = wor.getTicketId();
                        return wor;
                    }

                } else {
                    WorkOrder w = wdao.createWorkOrderV2(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername);
                    return w;

                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<WorkOrder> getWorkOrders(Integer queueId, Integer pageNo) {
        int count = 100;
        return wdao.findByQueueId(queueId, (pageNo - 1) * count, count);
    }

    public WorkOrder getWorkOrder(Integer ticketId) {
        return wdao.findByQueueTypeId(ticketId);
    }

    public WorkOrderEnumerationBody getWorkOrderEnum(Integer ticketId) {
        return wdao.findByTicketIdEnum(ticketId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateStaffCode(Integer counter) {
        while (counter != null) {
            counter = wdao.hasNextRecord(counter);
        }
    }

    public Awesome addToDisconnectionQueue(RequestObj r) {
        try {

            String queueTypeString = "select * from queue_type where token=(select config_value "
                    + "from emcc_config where config_key='disconnect_queue_type')";

            try (Connection emcc = DriverManager.getConnection(
                    "jdbc:mysql://172.29.12.3/wfm_new?useSSL=false", "wfm", "tombraider");
                    Statement queueStmt = emcc.createStatement();
                    Statement lastWorkOrderStmt = emcc.createStatement();
                    Statement engineerStmt = emcc.createStatement();) {

                ResultSet queueStmtQuery = queueStmt.executeQuery(queueTypeString);

                if (queueStmtQuery.next() == false) {
                    return StandardResponse.disconnectionQueueTypeNotSet();
                }

                Integer queueTypeId = null, queueId = null;

                while (queueStmtQuery.next()) {
                    queueTypeId = queueStmtQuery.getInt("id");
                    queueId = queueStmtQuery.getInt("queue_id");
                }

                int ticketId = 0;
                String sql = String.format("select * from work_order where reference_type_data = '%s'  and (current_status != '%s' or is_closed = %d) and queue_type_id = %d order by id desc limit 1", r.getBillingId(),
                        "CLOSED", 0, queueTypeId);

                ResultSet lastQueryStmt = lastWorkOrderStmt.executeQuery(sql);

                Integer found = null;

                String query = "select id as engineerId from engineer where user_id in (select id from users where staff_id = '%s') ";
                query = String.format(query, r.getStaffId());
                if (Optional.fromNullable(r.getStaffId()).isPresent()) {
                    ResultSet executeQuery = engineerStmt.executeQuery(query);

                    while (executeQuery.next()) {
                        found = executeQuery.getInt("engineerId");
                    }
                }

                if (lastQueryStmt.next() == false) {
                    ticketId = createWorkOrder(queueTypeId, queueId, r, found);
                    return StandardResponse.ok(ticketId);
                }
                Integer workOrderId = null;
                while (lastQueryStmt.next()) {
                    ticketId = lastQueryStmt.getInt("ticket_id");
                    workOrderId = lastQueryStmt.getInt("id");
                }

                addRemark("Emcc", workOrderId, r.getDescription(), 1, Double.valueOf(r.getAmount()));

                if (found != null) {

//                    wor.setEngineerId(new Engineer(found));
//                    wor.setIsAssigned(Short.valueOf("1"));
//                    wor.setDateAssigned(new Date());
//                    wor.setLastPaymentAmount(Double.valueOf(r.getLastPaidAmount() == null ? "0.00" : r.getLastPaidAmount()));
//                    wor.setLastPaymentDate(r.getLastPaymentDate());
//                    wor.setCurrentBill(Double.valueOf(r.getCurrentBill() == null ? "0.00" : r.getCurrentBill()));
//                    wor.setPreviousOutstanding(r.getPreviousOutstanding());
//                    wor.setDueDate(r.getDueDate());
//                    wor.setUpdateTime(new Date());
//                    wor.setAmount(Double.valueOf(r.getAmount() == null ? "0.00" : r.getAmount()));
//                    wor.setDescription(r.getDescription());
//
//                    wdao.edit(wor);
                }
                System.out.println(":::::: Ticket updated in disconnection queue :::::::::: " + ticketId);
                return StandardResponse.ok(ticketId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Integer createWorkOrder(Integer queueTypeId, Integer queueId, RequestObj r, Integer found) {

        reentrantLock.lock();
        int ticketId = 0;
        System.out.println(":::::: Waiting Threads to create work order :::::::" + reentrantLock.getQueueLength());
        try {

            String createWorkOrderPstmt = "insert into work_order (address_line_1,business_unit,amount,city,contact_number,current_bill,description,due_date,last_payment_amount,last_payment_date,previous_outstanding,is_closed,is_active,purpose,reported_by,summary,queue_type_id,create_time,current_status,priority,reference_type,state,channel,customer_tariff,reference_type_data,is_assigned,queue_id,token,debt_balance_amount,ticket_id,engineer_id,date_assigned,work_date,owner_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try (Connection emcc = DriverManager.getConnection(
                    "jdbc:mysql://172.29.12.3/wfm_new?useSSL=false", "wfm", "tombraider");
                    PreparedStatement ps1 = emcc.prepareStatement(createWorkOrderPstmt);
                    Statement findNextTicketStmt = emcc.createStatement();) {

                ResultSet executeQuery = findNextTicketStmt.executeQuery("select max(ticket_id) as maxTicket from work_order ");
                while (executeQuery.next()) {
                    ticketId = executeQuery.getInt("maxTicket") + 1;
                }

                ps1.setString(1, r.getAddress());
                ps1.setString(2, r.getBusinessUnit());
                ps1.setDouble(3, r.getAmount() == null ? Double.valueOf(0.00) : Double.valueOf(r.getAmount()));
                ps1.setString(4, r.getCity());
                ps1.setString(5, r.getPhone() == null ? "-" : r.getPhone());
                ps1.setDouble(6, r.getCurrentBill() == null ? Double.valueOf(0.00) : Double.valueOf(r.getCurrentBill()));
                ps1.setString(7, r.getDescription());
                ps1.setDate(8, new java.sql.Date(r.getDueDate().getTime()));
                ps1.setDouble(9, r.getLastPaidAmount() != null ? new Double(r.getLastPaidAmount()) : null);
                ps1.setDate(10, r.getLastPaidAmount() != null ? new java.sql.Date(r.getLastPaymentDate().getTime()) : null);
                ps1.setDouble(11, r.getPreviousOutstanding());
                ps1.setInt(12, 0);
                ps1.setInt(13, 1);
                ps1.setString(14, r.getPurpose());
                ps1.setString(15, r.getReportedBy());
                ps1.setString(16, r.getSummary());
                ps1.setInt(17, queueTypeId);
                ps1.setDate(18, new java.sql.Date(System.currentTimeMillis()));
                ps1.setString(19, "OPEN");
                ps1.setString(20, "Low");
                ps1.setString(21, "Billing ID");
                ps1.setString(22, "Lagos");
                ps1.setString(23, "Emcc");
                ps1.setString(24, r.getTariff());
                ps1.setString(25, r.getBillingId());
                ps1.setInt(26, found != null ? 1 : 0);
                ps1.setInt(27, queueId);
                ps1.setString(28, RandomStringUtils.randomAlphanumeric(30));
                ps1.setDouble(29, 0.0);
                ps1.setInt(30, ticketId);
                ps1.setInt(31, found != null ? found : null);
                ps1.setDate(32, found != null ? new java.sql.Date(System.currentTimeMillis()) : null);
                ps1.setDate(33, found != null ? new java.sql.Date(System.currentTimeMillis()) : null);
                ps1.setInt(34, 1);

                ps1.executeUpdate();

            }

            return ticketId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            reentrantLock.unlock();
        }

    }

    private Integer getEngineerIdByStaffId(String staffId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addRemark(String emcc, Integer workOrderId, String description, Integer ownerId, Double amount) {

        String createRemarkStmt = "insert into work_order_remark (work_order_id,token,owner_id,comment,create_time,channel) values (?,?,?,?,?,?)";
        try {
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://172.29.12.3/wfm_new?useSSL=false", "wfm", "tombraider");
                    PreparedStatement ps1 = conn.prepareStatement(createRemarkStmt);) {

                ps1.setInt(1, workOrderId);
                ps1.setString(2, RandomStringUtils.random(30));
                ps1.setInt(3, ownerId);
                ps1.setString(4, description);
                ps1.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                ps1.setString(6, emcc);

                ps1.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

}
