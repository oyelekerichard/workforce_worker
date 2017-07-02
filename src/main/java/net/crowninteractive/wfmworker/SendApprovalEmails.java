
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.dao.InventoryApprovalDao;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.misc.EmailText;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.misc.Config;

/**
 *
 * @author USER
 */
@Component
public class SendApprovalEmails extends TimerTask {

    @Autowired
    private InventoryApprovalDao inventoryApprovalDao;
    @Autowired
    private EmailSender emailSender;

    @Override
    public void run() {
    System.out.println("SendApprovalEmails starting...");
        try {
            List<InventoryApproval> inventoryApprovals = inventoryApprovalDao.findByEmailSendTimeNULL();
            System.out.println("SendApprovalEmails running...");

            for (InventoryApproval inventoryApproval : inventoryApprovals) {
                InventoryRequest request = inventoryApproval.getInventoryReqId();
                WorkOrder workOrderId = inventoryApproval.getWorkOrderId();
                String[] recipients = inventoryApproval.getRecipients().split(",");
                String template;
                template = EmailText.getApprovalContent();

                System.out.println("SendApprovalEmails processing "+ request);
               
                String url = Config.getInstance().wfmFrontendUrl();
                for (String recipient : recipients) {
                    String content = String.format(template,workOrderId.getSummary(), workOrderId.getDescription(), request.getItemName(), request.getLocationName(), request.getQuantity().toString(),url).replaceFirst("(%%)","%");

                    System.out.println("SendApprovalEmails to " + recipient);
                    emailSender.sendNoReplyEmail(
                            "INVENTORY APPROVAL FOR WORK ORDER #" + workOrderId.getTicketId(), recipient,
                            content);
                    System.out.println("SendApprovalEmails sent to " + recipient);
                }
                inventoryApproval.setEmailSendTime(new Date());
                inventoryApproval.setUpdateTime(new Date());
                inventoryApprovalDao.edit(inventoryApproval);
                System.out.println("SendApprovalEmails done processing inventory " + inventoryApproval);
            }
            System.out.println("SendApprovalEmails run ended successfully!");
        } catch (IOException ex) {
            System.out.println("SendApprovalEmails an exception occurred  " + ex);
            Logger.getLogger(SendApprovalEmails.class.getName()).log(Level.ALL, null, ex);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
