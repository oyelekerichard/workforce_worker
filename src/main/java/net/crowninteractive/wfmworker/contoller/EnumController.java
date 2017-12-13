/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

import net.crowninteractive.wfmworker.service.EnumService;
import net.crowninteractive.wfmworker.service.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
       return new ResponseEntity<String>("Test value",HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "approve_enum_work_order")
    public ResponseEntity approveEnumWorkOrders(@RequestBody Token tokens) {
        String message = enumService.approveWorkOrders(tokens);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

}
