/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Engineer;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderEnumerationBody;
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
            //fetch queueTypeToken
            QueueType qt = wdao.getEmccConfigDisconnectQueueTypeAndQueue();
            if (qt == null) {
                return StandardResponse.disconnectionQueueTypeNotSet();
            }

            int ticketId = 0;
            List<WorkOrder> wo = wdao.getLastWorkOrderinQueueType(r, qt.getId());

            if (wo.isEmpty()) {
                ticketId = wdao.createWorkOrder(qt, r);
                return StandardResponse.ok(ticketId);
            }
            WorkOrder wor = wo.get(0);
            wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), r.getDescription(), "1", Double.valueOf(r.getAmount()));

            Integer found = null;
            if (Optional.fromNullable(r.getStaffId()).isPresent()) {
                found = wdao.getEngineerIdByStaffId(r.getStaffId());
            }
//            else {
//                found = wdao.getEngineerIdByBook(r.getBillingId(), qt.getId());
//            }

            if (found != null) {

                wor.setEngineerId(new Engineer(found));
                wor.setIsAssigned(Short.valueOf("1"));
                wor.setDateAssigned(new Date());
                wor.setLastPaymentAmount(Double.valueOf(r.getLastPaidAmount() == null ? "0.00" : r.getLastPaidAmount()));
                wor.setLastPaymentDate(r.getLastPaymentDate());
                wor.setCurrentBill(Double.valueOf(r.getCurrentBill() == null ? "0.00" : r.getCurrentBill()));
                wor.setPreviousOutstanding(r.getPreviousOutstanding());
                wor.setDueDate(r.getDueDate());
                wor.setUpdateTime(new Date());
                wor.setAmount(Double.valueOf(r.getAmount() == null ? "0.00" : r.getAmount()));
                wor.setDescription(r.getDescription());

                wdao.edit(wor);
            }
            wor.setDateAssigned(new Date());
            ticketId = wor.getTicketId();
            System.out.println(":::::: Ticket updated in disconnection queue :::::::::: " + ticketId);
            return StandardResponse.ok(ticketId);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
