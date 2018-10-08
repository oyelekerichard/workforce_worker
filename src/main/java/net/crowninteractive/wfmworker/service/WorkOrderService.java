/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
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

    public Awesome addToDisconnectionQueue(String amount, String billingID, String businessUnit, String tarriff, String city, String address, String phone, String summary, String description, String reportedBy) {

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
                        ticketId = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername,amount);
                        return StandardResponse.ok(ticketId);
                    } else {
                        WorkOrder wor = wo.get(0);
                        wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), description, "1");
                        ticketId = wor.getTicketId();
                        return StandardResponse.ok(ticketId);
                    }

                } else {
                    int tid = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername,amount);
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

    public Awesome processItems(RequestObj[] reqList) {
        Awesome awe = null;
        for (RequestObj obj : reqList) {
            String desc = obj.getDescription().concat(String.format(" | Debt amount is %s Naira", obj.getAmount()));
            WorkOrder w = addToDisconnectionQueueV2(obj.getAmount(), obj.getBillingId(), obj.getBusinessUnit(), obj.getTariff(), obj.getCity(), obj.getAddress(), obj.getPhone(), obj.getSummary(), desc, obj.getReportedBy());
            //write message to queue
            template.send(disconnectionQueue, new MessageCreator() {
                @Override
                public Message createMessage(Session sn) throws JMSException {
                    TextMessage message = sn.createTextMessage();
                    Map<String, String> myMap = new HashMap<String, String>();
                    myMap.put("acctRecId", obj.getAcctRecId());
                    myMap.put("ticketId", String.valueOf(w.getTicketId()));

                    Gson gson = new GsonBuilder().create();
                    String msg = gson.toJson(myMap);
                    message.setText(msg);
                    return message;
                }
            });
        }

        return awe;
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
                        wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), description, "1");
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
        return wdao.findByQueueId(queueId,(pageNo-1)*count,count);
    }

    public WorkOrder getWorkOrder(Integer ticketId) {
       return  wdao.findByQueueTypeId(ticketId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateStaffCode(Integer counter) {
          while (counter != null) {
            counter = wdao.hasNextRecord(counter);
        }
    }

    
}
