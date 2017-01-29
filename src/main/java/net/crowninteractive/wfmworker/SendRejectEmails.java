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
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author USER
 */
public class SendRejectEmails extends TimerTask {
    
    @Autowired
    private EmailSender emailSender;
    
    @Autowired
    private InventoryApprovalDao inventoryApprovalDao;
    
    @Override
    public void run(){ 
        List<InventoryApproval> inventoryApprovals = inventoryApprovalDao.findByEmailSendTimeNULL();
       
        for (InventoryApproval inventoryApproval : inventoryApprovals) {
        
              
            System.out.println("net.crowninteractive.wfmworker.SendApprovalEmails.run()");
            InventoryRequest request=inventoryApproval.getInventoryReqId();
            WorkOrder workOrderId = inventoryApproval.getWorkOrderId();
            String[] recipients=inventoryApproval.getRecipients().split(",");
            for (String recipient : recipients) {
                emailSender.sendNoReplyEmail("INVENTORY APPROVAL FOR WORK ORDER" + workOrderId.getId().toString(), recipient,
                        "<p>This is to notify you that a request <br>for inventory was made as regards the above <br>workorder. Please find details below :</p>"+ "<p><b>Work Order Summary :</b> "+ workOrderId.getSummary()+"<br><b>Work Order Description :</b> "+workOrderId.getDescription()+"<br><b>Inventory Requested :</b> "+request.getItemName()+"<br><b>Location : </b>"+request.getLocationName()+"<br><b>Quantity :</b><br><br><br>"
                                + "<p>kindly approve or reject this request. THANKS</p>"


                );


            }
            System.out.println("net.crowninteractive.wfmworker.SendApprovalEmails.run()");
            Date currentdate=new Date();
            inventoryApproval.setUpdateTime(currentdate);
            inventoryApprovalDao.edit(inventoryApproval);

            System.out.println("net.crowninteractive.wfmworker.SendApprovalEmails.run()");
              
                 
       }
       
   
   }
}
