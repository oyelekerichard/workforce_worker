/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeMath.log;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnson3yo
 */
@Service
public class EnumService {
    
    @Autowired private WorkOrderDao wdao;

    public String approveWorkOrders(Token tokens) {
       
        try {
            if (tokens.getTokens() == null || tokens.getTokens().length == 0) {
              return String.format("Tokens must not be empty");
            } else {
               
                Integer success = 0, failure = 0, approved = 0;
                for (String token : tokens.getTokens()) {
                    WorkOrderTemp workOrderTemp = wdao.getEnumWorkOrderByToken(token);

                    if (workOrderTemp != null) {
                        if (workOrderTemp.getTicketId() == null) {
                            String customername = null;
                            String type = "a";
                            Awesome awe = getCustomerDetails(workOrderTemp.getReferenceTypeData(), type);
                            if (awe.getResp() == 0) {
                                Gson gson = new GsonBuilder().create();
                                Map jsonMap = gson.fromJson(gson.toJson(awe.getObject()), Map.class);
                                customername = (String) jsonMap.get("name");
                            }else{
                                customername = workOrderTemp.getCustomerName();
                            }
                            workOrderTemp.setCustomerName(customername);
                            wdao.approveEnumWorkOrder(workOrderTemp);
                            success++;
                        } else {
                            approved++;
                        }

                    } else {
                        failure++;
                    }

                }
                return String.format((success > 0 ? success + " work orders were successfully approved," : "") + (failure > 0 ? failure + " approval request failed," : "") + (approved > 0 ? approved + " already approved" : ""));
            }

        } catch (Exception e) {
          return e.getMessage();
        }
       
    }

    private Awesome getCustomerDetails(String referenceTypeData, String type) {
        try {
            HttpQuery httpQuery = new HttpQuery();
            String resp = httpQuery.getCustomerDetails(referenceTypeData, type);
            if (resp != null && !resp.isEmpty()) {
                Awesome a = new Gson().fromJson(resp, Awesome.class);
                return a;
            }
            System.out.print(resp);
            return StandardResponse.errorDuringProcessing();
        } catch (Exception e) {
            log(e, "getcategories");
            return StandardResponse.systemError();
        }
    }
    
}
