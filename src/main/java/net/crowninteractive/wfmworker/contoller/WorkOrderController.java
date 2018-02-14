/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.misc.Extension;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnson3yo
 */
@RestController
@RequestMapping("/work_order")
public class WorkOrderController extends Extension{
    
    @Autowired private WorkOrderService service;
    
    @RequestMapping(method = RequestMethod.GET, value = "emcc_disconnect")
    public String addToDisconnectQueue(RequestObj obj, @Context HttpServletRequest request) {
        Awesome awe;
        try {
            System.out.println(obj);
            //check deliquency upload 
                String desc = obj.getDescription().concat(String.format(" | Debt amount is %s Naira", obj.getAmount()));
                awe = service.addToDisconnectionQueue(obj.getAmount(),obj.getBillingId(), obj.getBusinessUnit(), obj.getTariff(), obj.getCity(), obj.getAddress(), obj.getPhone(), obj.getSummary(), desc, obj.getReportedBy());
          
        } catch (Exception ex) {
            awe = StandardResponse.invalidUser();
        }
        return process(awe, request);
    }
    
}
