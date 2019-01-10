/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import net.crowninteractive.wfmworker.dao.RequestObj;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderMessage;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.Extension;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnson3yo
 */
@RestController
@RequestMapping("work_order")
public class WorkOrderController extends Extension {

    @Autowired
    private WorkOrderService service;

    @RequestMapping(method = RequestMethod.POST, value = "emcc_disconnect")
    public String addToDisconnectQueue(@RequestBody RequestObj obj, @Context HttpServletRequest request) {
        Awesome awe;
        try {
            System.out.println(obj);
            //check deliquency upload
            String desc = obj.getDescription().concat(String.format(" | Debt amount is %s Naira", obj.getAmount()));
            awe = service.addToDisconnectionQueue(obj);
            System.out.println(awe);
        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.systemError(ex.getMessage());
        }
        return process(awe, request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "emcc_report_workorder")
    public String reportWorkOrder(@RequestBody WorkOrderMessage worder) {
        try {
            int ticketId = service.createWorkOrder(worder);
            Awesome awe = new Awesome(0, String.format("Work Order with Ticket ID : %d Created Successfully", ticketId));
            return new Gson().toJson(awe);
        } catch (WfmWorkerException ex) {
            Awesome awe = new Awesome(400, ex.getMessage());
            return new Gson().toJson(awe);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "emcc_disconnectv2")
    public String addToDisconnectQueueV2(@RequestBody RequestObj[] reqList, @Context HttpServletRequest request) {
        Awesome awe;
        try {
            //check deliquency upload
            awe = service.processItems(reqList);
            System.out.println(awe);
        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.invalidUser();
        }
        return process(awe, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{queueId}")
    public ResponseEntity getWorkOrders(@PathVariable("queueId") Integer queueId,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo) {
        List<WorkOrder> workorders = service.getWorkOrders(queueId, pageNo);
        return new ResponseEntity<List<WorkOrder>>(workorders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByTicketId/{ticketId}")
    public ResponseEntity getWorkOrder(@PathVariable("ticketId") Integer ticketId
    ) {
        try {
            WorkOrder workorder = service.getWorkOrder(ticketId);
            return new ResponseEntity<WorkOrder>(workorder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "staff_code/{counter}")
    public String staffCode(@Context HttpServletRequest request, @PathVariable("counter") Integer counter) {
        Awesome awesome;
        try {
            service.generateStaffCode(counter);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error occured";
        }

        return "my name is remi";
    }

}
