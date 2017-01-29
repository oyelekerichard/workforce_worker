/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import net.crowninteractive.wfmworker.dao.InventoryApprovalDao;
import net.crowninteractive.wfmworker.dao.InventoryRequestItemDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author USER
 */


@Component
public class Scheduler extends TimerTask{
    
    @Autowired
    private InventoryApprovalDao inventoryApprovalDao;
    
 
    @Override
    public void run(){
        System.out.print("i am here");
        List<InventoryApproval> inventoryApprovals = inventoryApprovalDao.findByEmailSendTimeNULL();

        for (InventoryApproval inventoryApproval : inventoryApprovals) {


            InventoryRequest request = inventoryApproval.getInventoryReqId();
            WorkOrder workOrderId = inventoryApproval.getWorkOrderId();
            String[] recipients = inventoryApproval.getRecipients().split(",");
            
            for (String recipient : recipients) {
                sendNoReplyEmail("INVENTORY APPROVAL FOR WORK ORDER" + workOrderId.getId().toString(), recipient,
                        "<p>This is to notify you that a request <br>for inventory was made as regards the above <br>workorder. Please find details below :</p>"+ "<p><b>Work Order Summary :</b> "+ workOrderId.getSummary()+"<br><b>Work Order Description :</b> "+workOrderId.getDescription()+"<br><b>Inventory Requested :</b> "+request.getItemName()+"<br><b>Location : </b>"+request.getLocationName()+"<br><b>Quantity :</b><br><br><br>"
                                + "<p>kindly approve or reject this request. THANKS</p>"


                        );


            }

            inventoryApproval.setEmailSendTime(new Date());
            inventoryApprovalDao.persist(inventoryApproval);
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
