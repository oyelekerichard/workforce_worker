/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
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

    @Autowired
    private EnumService enumService;

    @RequestMapping(method = RequestMethod.GET, value = "test")
    public ResponseEntity testEndpoint() {
        return new ResponseEntity<String>("Test value", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "approve_enum_work_order")
    public ResponseEntity approveEnumWorkOrders(@RequestBody Token tokens) {
        String message = enumService.approveWorkOrders(tokens);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "enumerationDashboard")
    public ResponseEntity dashboard(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "showWidget2", required = false) String flag1,
            @RequestParam(value = "showWidget1", required = false) String flag2) {
        Dashboard d = enumService.getDashboard(startDate, endDate,flag1,flag2);
        return new ResponseEntity<Dashboard>(d, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "updateWorkOrder")
    public ResponseEntity updateEnumerationWorkOrder(@RequestBody Map<String,String>update) {
        try {
            enumService.updateEnumWorkOrder(update);
        } catch (WfmWorkerException ex) {
            
            Logger.getLogger(EnumController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("enum work order update successful ", HttpStatus.OK);
    }
    

}
