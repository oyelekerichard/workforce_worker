/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import com.sun.jersey.api.spring.Autowire;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.crowninteractive.wfmworker.dao.InventoryApprovalDao;
import net.crowninteractive.wfmworker.dao.InventoryRequestItemDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.InventoryRequestItem;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author USER
 */


@Component("mybean")
public class Scheduler{
    @Autowired
    InventoryApprovalDao inventoryApprovalDao;
    @Autowired
    InventoryRequestItemDao inventoryRequestItemDao;
    @Autowired
    WorkOrderDao workOrderDao;
    @Autowired 
    EntityManagerFactory JU;
    
    
    
 
 public void work()
   { System.out.print("i am here");
       List<InventoryApproval> inventoryApprovals=inventoryApprovalDao.findByEmailSendTimeNULL();
       
       for (InventoryApproval inventoryApproval : inventoryApprovals) {
        
           
           InventoryRequest request=inventoryApproval.getInventoryReqId();
           WorkOrder workOrderId = inventoryApproval.getWorkOrderId();
           String[] recipients=inventoryApproval.getRecipients().split(",");
           for (String recipient : recipients) {
               sendNoReplyEmail("INVENTORY APPROVAL FOR WORK ORDER" + workOrderId.getId().toString(), recipient,
                       "<p>This is to notify you that a request <br>for inventory was made as regards the above <br>workorder. Please find details below :</p>"+ "<p><b>Work Order Summary :</b> "+ workOrderId.getSummary()+"<br><b>Work Order Description :</b> "+workOrderId.getDescription()+"<br><b>Inventory Requested :</b> "+request.getItemName()+"<br><b>Location : </b>"+request.getLocationName()+"<br><b>Quantity :</b><br><br><br>"
                               + "<p>kindly approve or reject this request. THANKS</p>"
                               
                       
                       );
               
               
           }
           
           inventoryApproval.setEmailSendTime(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
           JU.createEntityManager().persist(inventoryApproval);
       }
       
   
   }
    public int sendNoReplyEmail(String subject, String to, String msg) {


        HtmlEmail emailAgent = new HtmlEmail();

        emailAgent.setHostName("smtp.office365.com");
        emailAgent.setSmtpPort(587);
        emailAgent.setAuthentication("no-reply@ekedp.com", "CI@ekedp15");
        emailAgent.setTLS(true);
        try {
            emailAgent.setFrom("no-reply@ekedp.com");
            emailAgent.setSubject(subject);
            emailAgent.setCharset("utf8");
            emailAgent.setHtmlMsg(msg);
            emailAgent.addTo(to);

            emailAgent.send();
            return 1;
        } catch (EmailException e) {
            System.out.println("error while wending email: ffff "+e.getMessage());
            return 0;
        }            
       
        
        
    } 
    
}
