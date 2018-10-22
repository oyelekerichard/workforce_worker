/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import java.util.Map;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.EnumService;
import net.crowninteractive.wfmworker.service.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//        try {
//          //  enumService.updateEnumWorkOrder(update);
//        } catch (WfmWorkerException ex) {
//
//            Logger.getLogger(EnumController.class.getName()).log(Level.SEVERE, null, ex);
//            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity<String>("enum work order update successful ", HttpStatus.OK);
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

}
