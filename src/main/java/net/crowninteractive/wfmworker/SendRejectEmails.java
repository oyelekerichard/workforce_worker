
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.dao.InventoryRejectionDao;
import net.crowninteractive.wfmworker.entity.InventoryRejection;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.misc.EmailText;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

//~--- JDK imports ------------------------------------------------------------

/**
 *
 * @author USER
 */
public class SendRejectEmails extends TimerTask {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private InventoryRejectionDao inventoryRejectionDao;

    @Override
    public void run() {
    System.out.println("SendRejectEmails starting");
        try {
            List<InventoryRejection> inventoryRejections = inventoryRejectionDao.findByEmailSendTimeNULL();
            System.out.println("SendRejectEmails running...");

            for (InventoryRejection inventoryRejection : inventoryRejections) {
                InventoryRequest request = inventoryRejection.getRequestId();
                WorkOrder workOrderId = inventoryRejection.getWorkOrderId();
                String[] recipients = inventoryRejection.getRecipients().split(",");
                String name = inventoryRejection.getCreatedBy().getFirstname() + " " + inventoryRejection.getCreatedBy().getLastname();
                String template;

                template = EmailText.getRejectionContent();

                System.out.println("SendRejectEmails processing "+ request);

                for (String recipient : recipients) {
                    String content = String.format(template, name, workOrderId.getSummary(), workOrderId.getDescription(), request.getItemName(), request.getLocationName(), request.getQuantity()).replaceFirst("(%%)","%");
                    System.out.println("SendRejectEmails to " + recipient);
                    emailSender.sendNoReplyEmail(
                            "INVENTORY REJECTION FOR WORK ORDER" + workOrderId.getId().toString(), recipient,
                            content);
                    System.out.println("SendRejectEmails sent to " + recipient);
                }
                inventoryRejection.setEmailSendTime(new Date());
                inventoryRejection.setUpdateTime(new Date());
                inventoryRejectionDao.edit(inventoryRejection);
                System.out.println("SendRejectEmails done processing inventory " + inventoryRejection);
            }
            System.out.println("SendRejectEmails run ended successfully!");
        } catch (IOException ex) {
            System.out.println("SendRejectEmails an exception occurred  " + ex);
            Logger.getLogger(SendRejectEmails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
