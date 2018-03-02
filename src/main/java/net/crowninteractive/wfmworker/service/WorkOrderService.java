/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeMath.log;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnson3yo
 */
@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderDao wdao;

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
                        ticketId = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername);
                        return StandardResponse.ok(ticketId);
                    } else {
                                             
                        WorkOrder wor = wo.get(0);
                        wdao.addRemark("Emcc", String.valueOf(wor.getTicketId()), description, "1");
                        ticketId = wor.getTicketId();
                        return StandardResponse.ok(ticketId);
                    }

                } else {
                    int tid = wdao.createWorkOrder(qt, "", "1", businessUnit, summary, description, phone, city, address, tarriff, billingID, "EMCC", "", "", reportedBy, customername);
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

    

}
