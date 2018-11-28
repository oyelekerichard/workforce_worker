/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderEnumerationBody;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.EnumService;
import net.crowninteractive.wfmworker.service.Token;
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
@RequestMapping("/enum")
public class EnumController {

    private Logger L = Logger.getLogger("");

    @Autowired
    private EnumService enumService;
    
    @Autowired
    private WorkOrderService service;

    @RequestMapping(method = RequestMethod.GET, value = "test")
    public ResponseEntity testEndpoint() {
        return new ResponseEntity<String>("Test value", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "approve_enum_work_order")
    public ResponseEntity approveEnumWorkOrders(@RequestBody Token tokens) {
        String message = enumService.approveWorkOrders(tokens);
        return new ResponseEntity<Awesome>(new Awesome(0, message), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "enumerationDashboard")
    public ResponseEntity dashboard(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "showWidget2", required = false) String flag1,
            @RequestParam(value = "showWidget1", required = false) String flag2) {
        Dashboard d = enumService.getDashboard(startDate, endDate, flag1, flag2);
        return new ResponseEntity<Dashboard>(d, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateWorkOrder")
    public ResponseEntity updateEnumerationWorkOrder(@RequestBody Map<String, String> update) {

        return new ResponseEntity<String>("enum work order update successful ", HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "enumeration_requests")
    public Awesome getEnumerationRequests(
            @RequestParam(defaultValue = "1", value = "page") Integer page,
            @RequestParam(defaultValue = "business_unit", value = "district", required = false) String district,
            @RequestParam(defaultValue = "create_time", value = "from", required = false) String from,
            @RequestParam(defaultValue = "create_time", value = "to", required = false) String to,
            @RequestParam(value = "queue", required = false) String queue,
            @RequestParam(value = "queueType", required = false) String queueType,
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "billingId", required = false) String billingId,
            @RequestParam(value = "reportedBy", required = false) String reportedBy) {
        Awesome awe;
        try {
            awe = enumService.getRequestsList(district, from, to, page, queue, queueType, priority,
                    status, billingId,reportedBy);

        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.systemError();
        }
        return awe;
}

    @RequestMapping(method = RequestMethod.GET, value = "download_workorder")
    public Awesome downloadWorkOrder(@RequestParam("email") String emailAddress,
            @RequestParam(defaultValue = "business_unit", value = "district", required = false) String district,
            @RequestParam(defaultValue = "create_time", value = "from", required = false) String from,
            @RequestParam(defaultValue = "create_time", value = "to", required = false) String to,
            @RequestParam(value = "queue", required = false) String queue,
            @RequestParam(value = "queueType", required = false) String queueType,
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "billingId", required = false) String billingId,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            @RequestParam(value = "reportedBy", required = false) String reportedBy) {
        Awesome awe;
        try {
            awe = enumService.sendWorkOrderFile(district, from, to, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy, emailAddress);
            awe = StandardResponse.ok();
        } catch (Exception ex) {
            L.warning("An error occurred while trying to sendWorkOrderFileToUser " + emailAddress);
            awe = StandardResponse.errorDuringProcessing();
        }
        return awe;
    }

    @RequestMapping(method = RequestMethod.GET, value = "download_request")
    public Awesome downloadRequest(@RequestParam("email") String emailAddress,
            @RequestParam(defaultValue = "business_unit", value = "district", required = false) String district,
            @RequestParam(defaultValue = "create_time", value = "from", required = false) String from,
            @RequestParam(defaultValue = "create_time", value = "to", required = false) String to,
            @RequestParam(value = "queue", required = false) String queue,
            @RequestParam(value = "queueType", required = false) String queueType,
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "billingId", required = false) String billingId,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            @RequestParam(value = "reportedBy", required = false) String reportedBy) {
        Awesome awe;
        try {
            awe = enumService.sendRequestFile(district, from, to, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy, emailAddress);
            awe = StandardResponse.ok();
        } catch (Exception ex) {
            L.warning("An error occurred while trying to sendWorkOrderFileToUser " + emailAddress);
            awe = StandardResponse.errorDuringProcessing();
        }
        return awe;
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

    @RequestMapping(method = RequestMethod.GET, value = "/getByTicketIdEnum/{ticketId}")
    public ResponseEntity getWorkOrderEnum(@PathVariable("ticketId") Integer ticketId
    ) {
        try {
            WorkOrderEnumerationBody workorder = service.getWorkOrderEnum(ticketId);
            return new ResponseEntity<WorkOrderEnumerationBody>(workorder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
