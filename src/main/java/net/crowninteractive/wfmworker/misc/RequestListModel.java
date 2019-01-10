/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import com.dyfferential.vyral.common.util.DateTime;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author CROWN INTERACTIVE
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestListModel{
        
    private Integer id;
    private String queueId;
    private String queueTypeId;
    private Integer ticketId;
    private String referenceType;
    private String referenceTypeData;
    private String businessUnit;
    private String priority;
    private Date createTime;
    private String currentStatus;
    private String reportedBy;
    private String token;
    private String addressLine1;
    private String city;



    public RequestListModel(Object[] e) {
        this.id = (Integer) e[0];
        this.queueId = (String) e[1];
        this.queueTypeId = (String) e[2];
        this.ticketId = (Integer) e[3];
        this.referenceType = (String) e[4];
        this.referenceTypeData = (String) e[5];
        this.businessUnit = (String) e[6];
        this.priority = (String) e[7];
        this.createTime = new Date((((Timestamp) e[8])).getTime());
        this.currentStatus = (String) e[9];
        this.reportedBy = (String) e[10];
        this.token = (String) e[11];
        this.addressLine1 = (String) e[12];
        this.city = (String) e[13];

    }
   
}
