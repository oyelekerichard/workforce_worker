/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.entity.EnumReportObj;
import net.crowninteractive.wfmworker.entity.Queue;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.Utils;
import net.crowninteractive.wfmworker.misc.WorkOrderEnumerationBody;
import net.crowninteractive.wfmworker.misc.WorkOrderJson;
import net.crowninteractive.wfmworker.service.Awesome;
import net.crowninteractive.wfmworker.service.EnumService;
import net.crowninteractive.wfmworker.service.Token;
import net.crowninteractive.wfmworker.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @RequestMapping(method = RequestMethod.POST, value = "create_enumeration_work_order")
    public Awesome createEnumerationWorkOrder(@RequestBody WorkOrderJson obj, @Context HttpServletRequest request) {

        try {
            return enumService.createEnumerationWorkOrder(obj);
        } catch (Exception ex) {
            return new Awesome(400, ex.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "approve_enum_work_order")
    public ResponseEntity approveEnumWorkOrders(@RequestBody Token tokens) {
        String message = enumService.approveWorkOrders(tokens);
        return new ResponseEntity<Awesome>(new Awesome(0, message), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "report/filter")
    public Awesome enumerationReport(
            @RequestParam(value = "businessDistrict", required = false) String businessDistrict,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate) {
        Map map = null;
        try {
            Object[] count = enumService.enumerationReport(businessDistrict, fromDate, toDate);
            map = new HashMap<String, Long>();
            map.put("workOrderCount", count[0]);
            map.put("requestCount", count[1]);
            return new Awesome(0, "successful", map);
        } catch (Exception e) {
            return new Awesome(400, e.getMessage());
        }

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

    @RequestMapping(method = RequestMethod.GET, value = "enumeration_requests/{token}")
    public Awesome getEnumerationRequestByToken(@PathVariable("token") String token) {
        Awesome awe;
        try {
            awe = enumService.getEnumRequestByToken(token);

        } catch (Exception ex) {
            awe = StandardResponse.errorDuringProcessing();
        }
        return awe;
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
            awe = enumService.getEnumRequestsList(district, from, to, page, queue, queueType, priority,
                    status, billingId, reportedBy);

        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.systemError();
        }
        return awe;
    }

    @RequestMapping(method = RequestMethod.GET, value = "enumeration_work_orders")
    public Awesome getEnumerationWorkOrders(
            @RequestParam(defaultValue = "1", value = "page") Integer page,
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
            awe = enumService.getEnumWorkOrderList(district, from, to, page, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy);

        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.systemError();
        }
        return awe;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "download_enumeration_requests")
    public ResponseEntity downloadEnumerationRequests(@RequestBody Token tokens) throws IOException {
        L.entering("download_work_orders", Arrays.toString(tokens.getTokens()));
        final String elementName = "tokens";
        if (tokens.getTokens().length != -1) {
            final String[] tokns = tokens.getTokens();
            final File requestFile = enumService.createEnumerationWorkOrderTempRequestFile(tokns);
            if (requestFile.isFile()) {
                Path path = Paths.get(requestFile.getAbsolutePath());
                byte[] data = Files.readAllBytes(path);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=enumeration_request_download.xls")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
                
            } else {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("no Record found for " + elementName));
            }
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("no element" + elementName + "found in JsonObject"));
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "download_enumeration_work_orders")
    public ResponseEntity downloadEnumerationWorkOrders(@RequestBody Token tokens) throws IOException {
        L.entering("download_work_orders", Arrays.toString(tokens.getTokens()));
        final String elementName = "tokens";
        if (tokens.getTokens().length != -1) {
            final String[] tokns = tokens.getTokens();
            final File workOrderFile = enumService.createEnumerationWorkOrderFile(tokns);
            if (workOrderFile.isFile()) {
                Path path = Paths.get(workOrderFile.getAbsolutePath());
                byte[] data = Files.readAllBytes(path);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=enumeration_work_orders_download.xls")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
                
            } else {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("no Record found for " + elementName));
            }
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("no element" + elementName + "found in JsonObject"));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "email_enumeration_work_orders/{emailAddress:.+}")
    public Awesome emailWorkOrderList(@PathVariable("emailAddress") String emailAddress,
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
            awe = enumService.sendEnumerationWorkOrderFile(emailAddress, district, from, to, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy);
            awe = StandardResponse.ok();
        } catch (Exception ex) {
            L.warning("An error occurred while trying to sendWorkOrderFileToUser " + emailAddress);
            awe = StandardResponse.errorDuringProcessing();
        }
        return awe;
    }

    @RequestMapping(method = RequestMethod.GET, value = "email_enumeration_requests/{emailAddress:.+}")
    public Awesome emailRequestList(@PathVariable("emailAddress") String emailAddress,
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
            awe = enumService.sendEnumerationRequestListFile(emailAddress, district, from, to, queue, queueType, priority, status, billingId, ticketId, reportedBy);
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
    
    @RequestMapping(method = RequestMethod.GET, value = "/queue_type/{token}")
    public Awesome getqueuetype(@PathVariable("token") String id) {
        Awesome awe;
        try {
            if (id != null && id.equals(((Queue) enumService.getEnumerationQueue().getObject()).getToken())) {
                awe = enumService.getqueueTypeByQueueid(id);
            } else {
                awe = StandardResponse.validationErrors("Invalid queue token");
            }
        } catch (Exception ex) {
            awe = StandardResponse.invalidUser();
        }
        return awe;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "download_enumeration_reports/{file_name}")
    public ResponseEntity downloadEnumerationReport(@PathVariable("file_name") String file_name) throws IOException {
        L.entering("getting work_orders for file_name", file_name);
        if (!Utils.checkNullOrEmpty(file_name)) {
            final File requestFile = new File("/var/wfm/downloads/" + file_name);
            if (requestFile.isFile()) {
                Path path = Paths.get(requestFile.getAbsolutePath());
                byte[] data = Files.readAllBytes(path);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename = "+file_name)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);            
            } else {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("no report found for " + file_name));
            }
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(StandardResponse.validationErrors("File name should be passed as a parameter"));
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "create_enumeration_report")
    public Awesome createEnumerationReport(@RequestBody String json) {
        System.out.println(json);
        Awesome awe;
        try {
            EnumReportObj repObj = new Gson().fromJson(json, EnumReportObj.class);
            awe = enumService.createEnumerationReportFile(repObj);

        } catch (Exception ex) {
            ex.printStackTrace();
            awe = StandardResponse.errorDuringProcessing();
        }
        return awe;
    }

}
