/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.Extension;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import static org.hibernate.annotations.SourceType.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnson3yo
 */
@RestController
@RequestMapping("work_order")
public class WorkOrderController extends Extension{
    
    @Autowired private WorkOrderService service;
    
    @RequestMapping(method = RequestMethod.POST, value = "emcc_disconnect")
    public String addToDisconnectQueue(@RequestBody RequestObj obj, @Context HttpServletRequest request) {
        Awesome awe;
        try {
            System.out.println(obj);
            //check deliquency upload 
                String desc = obj.getDescription().concat(String.format(" | Debt amount is %s Naira", obj.getAmount()));
                awe = service.addToDisconnectionQueue(obj.getAmount(),obj.getBillingId(), obj.getBusinessUnit(), obj.getTariff(), obj.getCity(), obj.getAddress(), obj.getPhone(), obj.getSummary(), desc, obj.getReportedBy());
                System.out.println(awe);
        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.invalidUser();
        }
        return process(awe, request);
    }
    
      
     @RequestMapping(method = RequestMethod.POST, value = "emcc_report_workorder")
    public String reportWorkOrder(WorkOrderMessage worder) {
        try {
            int ticketId = service.createWorkOrder(worder);
            Awesome awe = new Awesome(0, String.format("Work Order with Ticket ID : %d Created Successfully", ticketId));
            return new Gson().toJson(awe);
        } catch (WfmWorkerException ex) {
            Awesome awe = new Awesome(400, ex.getMessage());
            return new Gson().toJson(awe);
        }

    }
    
}
