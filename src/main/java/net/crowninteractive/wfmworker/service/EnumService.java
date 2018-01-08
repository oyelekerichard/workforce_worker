/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeMath.log;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.misc.Config;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
                return String.format((success > 0 ? success + " work order(s) were successfully approved," : "") + (failure > 0 ? failure + " approval request failed," : "") + (approved > 0 ? approved + " already approved" : ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
          return e.getMessage();
        }
       
    }
    
    
    public Awesome getCustomerDetails(String number, String type) throws IOException {
        Config c = Config.getInstance();
       
        HashMap<String, String> queryparams = new HashMap<String, String>();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(c.getEMCCGetCustomerDetailsURL());

        if (type.toLowerCase().equals("a")) {
            builder.queryParam("accountNumber", number);
        } else {
            builder.queryParam("meterNumber", number);
        }
        RestTemplate restTemplate = new RestTemplate();
        Awesome response = restTemplate.getForObject(builder.toUriString(), Awesome.class);
        return response;
        
    }

    
    
}
